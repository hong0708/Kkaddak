// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

interface IKATToken {
    function totalSupply() external view returns (uint256);
    function balanceOf(address account) external view returns (uint256);
    function transfer(address from, address recipient, uint256 amount, string calldata transferType) external returns (bool);
    function allowance(address owner, address spender) external view returns (uint256);
    function approve(address spender, uint256 amount) external returns (bool);
    function transferFrom(address sender, address recipient, uint256 amount) external returns (bool);

    event Transfer(address indexed from, address indexed to, uint256 value);
    event MsgSender(address msgSender);
    event Approval(address indexed owner, address indexed spender, uint256 value);
}

// ERC721 토큰을 기반으로 한 "MusicNFT"라는 스마트 컨트랙트를 정의
contract MusicNFT is ERC721 {

    using Counters for Counters.Counter; // Counters 라이브러리를 사용하여 ID를 자동으로 증가시킴
    Counters.Counter private _tokenIds; // 지금까지 발급받은 nft 개수

    IKATToken private katToken;

    struct MusicNFTData {
        string nftImageUrl;
        string coverImageUrl;
        string creatorNickname;
        uint256 createdDate;
        string trackTitle;
        string combination;
    }

    struct SaleHistory {
        address seller;
        address buyer;
        uint256 price;
        uint256 timestamp;
    }

    struct SaleInfo {
        bool isSelling;
        uint256 price;
    }

    struct MusicNFTMetaData{
        string nftImageUrl;
        uint256 tokenId;
    }

    event LogEvent(address msgSender, string logMsg);

    // 토큰ID에 따라 struct 저장
    mapping(uint256 => MusicNFTData) private musicNFTData;
    mapping(uint256 => SaleHistory[]) private tokenSaleHistories;
    mapping(uint256 => SaleInfo) private nftSaleInfo;

    constructor(address katTokenAddress) ERC721("MusicNFT", "MUSIC") {
        katToken = IKATToken(katTokenAddress);
    }

    function mintMusicNFT(
        address to,
        string memory coverImageUrl,
        string memory creatorNickname,
        string memory trackTitle,
        string memory nftImageUrl,
        string memory combination
    ) public returns (uint256) {
        _tokenIds.increment();
        uint256 tokenId = _tokenIds.current();

        MusicNFTData memory metadata = MusicNFTData({
            coverImageUrl: coverImageUrl,
            creatorNickname: creatorNickname,
            createdDate: block.timestamp,
            trackTitle: trackTitle,
            nftImageUrl: nftImageUrl,
            combination: combination
        });

        SaleInfo memory saleInfo = SaleInfo({
            isSelling: false,
            price: 0
        });

        musicNFTData[tokenId] = metadata;
        nftSaleInfo[tokenId] = saleInfo;
        _mint(to, tokenId);

        return tokenId;
    }

    function sellMusicNFT(uint256 tokenId, uint256 price) public {
        require(msg.sender == ownerOf(tokenId), "You do not have permission for that request.");
        SaleInfo storage info = nftSaleInfo[tokenId];
        info.isSelling = true;
        info.price = price;
    }

    function buyMusicNFT(uint256 tokenId, uint256 payment) public {
        require(_exists(tokenId), "Token ID not found");
        SaleInfo storage info = nftSaleInfo[tokenId];
        require(info.isSelling, "Not For Sale");
        require(payment >= info.price, "Not Enough Payment");
        require(katToken.balanceOf(msg.sender) >= payment, "Not enough KAT tokens");
        address seller = ownerOf(tokenId);

        require(katToken.transfer(msg.sender, seller, payment, "NFT"), "payment error.");
        _transfer(seller, msg.sender, tokenId); // NFT 소유권 이전

        info.isSelling = false;

        SaleHistory memory saleHistory = SaleHistory({
            seller: seller,
            buyer: msg.sender,
            price: payment,
            timestamp: block.timestamp
        });
        tokenSaleHistories[tokenId].push(saleHistory);
    }

    function getMusicNFTData(uint256 tokenId) public view returns (MusicNFTData memory) {
        require(_exists(tokenId), "Token ID not found");
        return musicNFTData[tokenId];
    }

    function getSaleHistory(uint256 tokenId) public view returns (SaleHistory[] memory) {
        require(_exists(tokenId), "Token ID not found");
        return tokenSaleHistories[tokenId];
    }

    function getTokenSaleInfo(uint256 tokenId) public view returns (SaleInfo memory) {
        require(_exists(tokenId), "Token ID not found");
        return nftSaleInfo[tokenId];
    }


    function getTokensOfOwner(address owner) public view returns (MusicNFTMetaData[] memory) {
        uint256 tokenCount = balanceOf(owner);
        MusicNFTMetaData[] memory nftMetaData = new MusicNFTMetaData[](tokenCount);
        uint256 counter = 0;
        for (uint256 i = 1; i <= _tokenIds.current(); i++) {
            if (_exists(i) && ownerOf(i) == owner) {
                nftMetaData[counter] = MusicNFTMetaData({
                    nftImageUrl: musicNFTData[i].nftImageUrl,
                    tokenId: i
                });
                counter++;
            }
        }
        return nftMetaData;
    }
}