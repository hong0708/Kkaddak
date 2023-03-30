package com.ssafy.kkaddak.common.util;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class NFT_sol_MusicNFT extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162002550380380620025508339810160408190526200003491620000bd565b60405180604001604052806008815260200167135d5cda58d3919560c21b815250604051806040016040528060058152602001644d5553494360d81b815250816000908162000084919062000194565b50600162000093828262000194565b5050600780546001600160a01b0319166001600160a01b0393909316929092179091555062000260565b600060208284031215620000d057600080fd5b81516001600160a01b0381168114620000e857600080fd5b9392505050565b634e487b7160e01b600052604160045260246000fd5b600181811c908216806200011a57607f821691505b6020821081036200013b57634e487b7160e01b600052602260045260246000fd5b50919050565b601f8211156200018f57600081815260208120601f850160051c810160208610156200016a5750805b601f850160051c820191505b818110156200018b5782815560010162000176565b5050505b505050565b81516001600160401b03811115620001b057620001b0620000ef565b620001c881620001c1845462000105565b8462000141565b602080601f831160018114620002005760008415620001e75750858301515b600019600386901b1c1916600185901b1785556200018b565b600085815260208120601f198616915b82811015620002315788860151825594840194600190910190840162000210565b5085821015620002505787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b6122e080620002706000396000f3fe608060405234801561001057600080fd5b506004361061012c5760003560e01c80636352211e116100ad578063b88d4fde11610071578063b88d4fde146102b9578063c87b56dd146102cc578063e05d541d146102df578063e985e9c5146102ff578063f2c391541461033b57600080fd5b80636352211e1461025757806370a082311461026a578063846697f51461028b57806395d89b411461029e578063a22cb465146102a657600080fd5b806323b872dd116100f457806323b872dd146101de578063349c9652146101f157806342842e0e146102045780635569105d146102175780635de6dc551461023757600080fd5b8063018f83431461013157806301ffc9a71461016657806306fdde0314610189578063081812fc1461019e578063095ea7b3146101c9575b600080fd5b61014461013f3660046119ea565b61034e565b6040805182511515815260209283015192810192909252015b60405180910390f35b610179610174366004611a19565b6103bf565b604051901515815260200161015d565b610191610411565b60405161015d9190611a86565b6101b16101ac3660046119ea565b6104a3565b6040516001600160a01b03909116815260200161015d565b6101dc6101d7366004611ab5565b6104ca565b005b6101dc6101ec366004611adf565b6105df565b6101dc6101ff366004611b1b565b610610565b6101dc610212366004611adf565b6106ae565b61022a6102253660046119ea565b6106c9565b60405161015d9190611b3d565b61024a610245366004611be2565b610a2a565b60405161015d9190611bfd565b6101b16102653660046119ea565b610bd2565b61027d610278366004611be2565b610c32565b60405190815260200161015d565b61027d610299366004611d11565b610cb8565b610191610dd7565b6101dc6102b4366004611e00565b610de6565b6101dc6102c7366004611e37565b610df5565b6101916102da3660046119ea565b610e2d565b6102f26102ed3660046119ea565b610ea1565b60405161015d9190611eb3565b61017961030d366004611f23565b6001600160a01b03918216600090815260056020908152604080832093909416825291909152205460ff1690565b6101dc610349366004611b1b565b610f5e565b604080518082019091526000808252602082015261036b8261124f565b6103905760405162461bcd60e51b815260040161038790611f56565b60405180910390fd5b506000908152600a60209081526040918290208251808401909352805460ff1615158352600101549082015290565b60006001600160e01b031982166380ac58cd60e01b14806103f057506001600160e01b03198216635b5e139f60e01b145b8061040b57506301ffc9a760e01b6001600160e01b03198316145b92915050565b60606000805461042090611f82565b80601f016020809104026020016040519081016040528092919081815260200182805461044c90611f82565b80156104995780601f1061046e57610100808354040283529160200191610499565b820191906000526020600020905b81548152906001019060200180831161047c57829003601f168201915b5050505050905090565b60006104ae8261126c565b506000908152600460205260409020546001600160a01b031690565b60006104d582610bd2565b9050806001600160a01b0316836001600160a01b0316036105425760405162461bcd60e51b815260206004820152602160248201527f4552433732313a20617070726f76616c20746f2063757272656e74206f776e656044820152603960f91b6064820152608401610387565b336001600160a01b038216148061055e575061055e813361030d565b6105d05760405162461bcd60e51b815260206004820152603d60248201527f4552433732313a20617070726f76652063616c6c6572206973206e6f7420746f60448201527f6b656e206f776e6572206f7220617070726f76656420666f7220616c6c0000006064820152608401610387565b6105da83836112bf565b505050565b6105e9338261132d565b6106055760405162461bcd60e51b815260040161038790611fbc565b6105da8383836113ac565b61061982610bd2565b6001600160a01b0316336001600160a01b03161461068e5760405162461bcd60e51b815260206004820152602c60248201527f596f7520646f206e6f742068617665207065726d697373696f6e20666f72207460448201526b3430ba103932b8bab2b9ba1760a11b6064820152608401610387565b6000918252600a6020526040909120805460ff1916600190811782550155565b6105da83838360405180602001604052806000815250610df5565b6107026040518060c001604052806060815260200160608152602001606081526020016000815260200160608152602001606081525090565b61070b8261124f565b6107275760405162461bcd60e51b815260040161038790611f56565b60008281526008602052604090819020815160c0810190925280548290829061074f90611f82565b80601f016020809104026020016040519081016040528092919081815260200182805461077b90611f82565b80156107c85780601f1061079d576101008083540402835291602001916107c8565b820191906000526020600020905b8154815290600101906020018083116107ab57829003601f168201915b505050505081526020016001820180546107e190611f82565b80601f016020809104026020016040519081016040528092919081815260200182805461080d90611f82565b801561085a5780601f1061082f5761010080835404028352916020019161085a565b820191906000526020600020905b81548152906001019060200180831161083d57829003601f168201915b5050505050815260200160028201805461087390611f82565b80601f016020809104026020016040519081016040528092919081815260200182805461089f90611f82565b80156108ec5780601f106108c1576101008083540402835291602001916108ec565b820191906000526020600020905b8154815290600101906020018083116108cf57829003601f168201915b505050505081526020016003820154815260200160048201805461090f90611f82565b80601f016020809104026020016040519081016040528092919081815260200182805461093b90611f82565b80156109885780601f1061095d57610100808354040283529160200191610988565b820191906000526020600020905b81548152906001019060200180831161096b57829003601f168201915b505050505081526020016005820180546109a190611f82565b80601f01602080910402602001604051908101604052809291908181526020018280546109cd90611f82565b8015610a1a5780601f106109ef57610100808354040283529160200191610a1a565b820191906000526020600020905b8154815290600101906020018083116109fd57829003601f168201915b5050505050815250509050919050565b60606000610a3783610c32565b905060008167ffffffffffffffff811115610a5457610a54611c65565b604051908082528060200260200182016040528015610a9a57816020015b604080518082019091526060815260006020820152815260200190600190039081610a725790505b509050600060015b6006548111610bc857610ab48161124f565b8015610ad95750856001600160a01b0316610ace82610bd2565b6001600160a01b0316145b15610bb65760408051808201825260008381526008602052919091208054829190610b0390611f82565b80601f0160208091040260200160405190810160405280929190818152602001828054610b2f90611f82565b8015610b7c5780601f10610b5157610100808354040283529160200191610b7c565b820191906000526020600020905b815481529060010190602001808311610b5f57829003601f168201915b5050505050815260200182815250838381518110610b9c57610b9c612009565b60200260200101819052508180610bb29061201f565b9250505b80610bc08161201f565b915050610aa2565b5090949350505050565b6000818152600260205260408120546001600160a01b03168061040b5760405162461bcd60e51b8152602060048201526018602482015277115490cdcc8c4e881a5b9d985b1a59081d1bdad95b88125160421b6044820152606401610387565b60006001600160a01b038216610c9c5760405162461bcd60e51b815260206004820152602960248201527f4552433732313a2061646472657373207a65726f206973206e6f7420612076616044820152683634b21037bbb732b960b91b6064820152608401610387565b506001600160a01b031660009081526003602052604090205490565b6000610cc8600680546001019055565b6000610cd360065490565b6040805160c08101825286815260208082018b90528183018a90524260608301526080820189905260a082018790528251808401845260008082528183018190528581526008909252929020815193945090928391908190610d359082612094565b5060208201516001820190610d4a9082612094565b5060408201516002820190610d5f9082612094565b506060820151600382015560808201516004820190610d7e9082612094565b5060a08201516005820190610d939082612094565b5050506000838152600a602090815260409091208251815460ff191690151517815590820151600190910155610dc98a84611510565b509098975050505050505050565b60606001805461042090611f82565b610df133838361167d565b5050565b610dff338361132d565b610e1b5760405162461bcd60e51b815260040161038790611fbc565b610e278484848461174b565b50505050565b6060610e388261126c565b6000610e4f60408051602081019091526000815290565b90506000815111610e6f5760405180602001604052806000815250610e9a565b80610e798461177e565b604051602001610e8a929190612154565b6040516020818303038152906040525b9392505050565b6060610eac8261124f565b610ec85760405162461bcd60e51b815260040161038790611f56565b600082815260096020908152604080832080548251818502810185019093528083529193909284015b82821015610f53576000848152602090819020604080516080810182526004860290920180546001600160a01b039081168452600180830154909116848601526002820154928401929092526003015460608301529083529092019101610ef1565b505050509050919050565b610f678261124f565b610f835760405162461bcd60e51b815260040161038790611f56565b6000828152600a60205260409020805460ff16610fd15760405162461bcd60e51b815260206004820152600c60248201526b4e6f7420466f722053616c6560a01b6044820152606401610387565b806001015482101561101a5760405162461bcd60e51b8152602060048201526012602482015271139bdd08115b9bdd59da0814185e5b595b9d60721b6044820152606401610387565b6007546040516370a0823160e01b815233600482015283916001600160a01b0316906370a0823190602401602060405180830381865afa158015611062573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906110869190612183565b10156110cc5760405162461bcd60e51b81526020600482015260156024820152744e6f7420656e6f756768204b415420746f6b656e7360581b6044820152606401610387565b60006110d784610bd2565b600754604051636abd941360e11b81523360048201526001600160a01b0380841660248301526044820187905260806064830152600360848301526213919560ea1b60a483015292935091169063d57b28269060c4016020604051808303816000875af115801561114c573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190611170919061219c565b6111ad5760405162461bcd60e51b815260206004820152600e60248201526d3830bcb6b2b73a1032b93937b91760911b6044820152606401610387565b6111b88133866113ac565b815460ff1916909155604080516080810182526001600160a01b0392831681523360208083019182528284019586524260608401908152600097885260098252938720805460018181018355918952919097209251600490910290920180549285166001600160a01b0319938416178155905195810180549690941695909116949094179091559051600283015551600390910155565b6000908152600260205260409020546001600160a01b0316151590565b6112758161124f565b6112bc5760405162461bcd60e51b8152602060048201526018602482015277115490cdcc8c4e881a5b9d985b1a59081d1bdad95b88125160421b6044820152606401610387565b50565b600081815260046020526040902080546001600160a01b0319166001600160a01b03841690811790915581906112f482610bd2565b6001600160a01b03167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92560405160405180910390a45050565b60008061133983610bd2565b9050806001600160a01b0316846001600160a01b0316148061138057506001600160a01b0380821660009081526005602090815260408083209388168352929052205460ff165b806113a45750836001600160a01b0316611399846104a3565b6001600160a01b0316145b949350505050565b826001600160a01b03166113bf82610bd2565b6001600160a01b0316146113e55760405162461bcd60e51b8152600401610387906121b9565b6001600160a01b0382166114475760405162461bcd60e51b8152602060048201526024808201527f4552433732313a207472616e7366657220746f20746865207a65726f206164646044820152637265737360e01b6064820152608401610387565b826001600160a01b031661145a82610bd2565b6001600160a01b0316146114805760405162461bcd60e51b8152600401610387906121b9565b600081815260046020908152604080832080546001600160a01b03199081169091556001600160a01b0387811680865260038552838620805460001901905590871680865283862080546001019055868652600290945282852080549092168417909155905184937fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef91a4505050565b6001600160a01b0382166115665760405162461bcd60e51b815260206004820181905260248201527f4552433732313a206d696e7420746f20746865207a65726f20616464726573736044820152606401610387565b61156f8161124f565b156115bc5760405162461bcd60e51b815260206004820152601c60248201527f4552433732313a20746f6b656e20616c7265616479206d696e746564000000006044820152606401610387565b6115c58161124f565b156116125760405162461bcd60e51b815260206004820152601c60248201527f4552433732313a20746f6b656e20616c7265616479206d696e746564000000006044820152606401610387565b6001600160a01b038216600081815260036020908152604080832080546001019055848352600290915280822080546001600160a01b0319168417905551839291907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908290a45050565b816001600160a01b0316836001600160a01b0316036116de5760405162461bcd60e51b815260206004820152601960248201527f4552433732313a20617070726f766520746f2063616c6c6572000000000000006044820152606401610387565b6001600160a01b03838116600081815260056020908152604080832094871680845294825291829020805460ff191686151590811790915591519182527f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a3505050565b6117568484846113ac565b61176284848484611811565b610e275760405162461bcd60e51b8152600401610387906121fe565b6060600061178b83611912565b600101905060008167ffffffffffffffff8111156117ab576117ab611c65565b6040519080825280601f01601f1916602001820160405280156117d5576020820181803683370190505b5090508181016020015b600019016f181899199a1a9b1b9c1cb0b131b232b360811b600a86061a8153600a85049450846117df57509392505050565b60006001600160a01b0384163b1561190757604051630a85bd0160e11b81526001600160a01b0385169063150b7a0290611855903390899088908890600401612250565b6020604051808303816000875af1925050508015611890575060408051601f3d908101601f1916820190925261188d9181019061228d565b60015b6118ed573d8080156118be576040519150601f19603f3d011682016040523d82523d6000602084013e6118c3565b606091505b5080516000036118e55760405162461bcd60e51b8152600401610387906121fe565b805181602001fd5b6001600160e01b031916630a85bd0160e11b1490506113a4565b506001949350505050565b60008072184f03e93ff9f4daa797ed6e38ed64bf6a1f0160401b83106119515772184f03e93ff9f4daa797ed6e38ed64bf6a1f0160401b830492506040015b6d04ee2d6d415b85acef8100000000831061197d576d04ee2d6d415b85acef8100000000830492506020015b662386f26fc10000831061199b57662386f26fc10000830492506010015b6305f5e10083106119b3576305f5e100830492506008015b61271083106119c757612710830492506004015b606483106119d9576064830492506002015b600a831061040b5760010192915050565b6000602082840312156119fc57600080fd5b5035919050565b6001600160e01b0319811681146112bc57600080fd5b600060208284031215611a2b57600080fd5b8135610e9a81611a03565b60005b83811015611a51578181015183820152602001611a39565b50506000910152565b60008151808452611a72816020860160208601611a36565b601f01601f19169290920160200192915050565b602081526000610e9a6020830184611a5a565b80356001600160a01b0381168114611ab057600080fd5b919050565b60008060408385031215611ac857600080fd5b611ad183611a99565b946020939093013593505050565b600080600060608486031215611af457600080fd5b611afd84611a99565b9250611b0b60208501611a99565b9150604084013590509250925092565b60008060408385031215611b2e57600080fd5b50508035926020909101359150565b602081526000825160c06020840152611b5960e0840182611a5a565b90506020840151601f1980858403016040860152611b778383611a5a565b92506040860151915080858403016060860152611b948383611a5a565b92506060860151608086015260808601519150808584030160a0860152611bbb8383611a5a565b925060a08601519150808584030160c086015250611bd98282611a5a565b95945050505050565b600060208284031215611bf457600080fd5b610e9a82611a99565b60006020808301818452808551808352604092508286019150828160051b87010184880160005b83811015610dc957888303603f1901855281518051878552611c4888860182611a5a565b918901519489019490945294870194925090860190600101611c24565b634e487b7160e01b600052604160045260246000fd5b600067ffffffffffffffff80841115611c9657611c96611c65565b604051601f8501601f19908116603f01168101908282118183101715611cbe57611cbe611c65565b81604052809350858152868686011115611cd757600080fd5b858560208301376000602087830101525050509392505050565b600082601f830112611d0257600080fd5b610e9a83833560208501611c7b565b60008060008060008060c08789031215611d2a57600080fd5b611d3387611a99565b9550602087013567ffffffffffffffff80821115611d5057600080fd5b611d5c8a838b01611cf1565b96506040890135915080821115611d7257600080fd5b611d7e8a838b01611cf1565b95506060890135915080821115611d9457600080fd5b611da08a838b01611cf1565b94506080890135915080821115611db657600080fd5b611dc28a838b01611cf1565b935060a0890135915080821115611dd857600080fd5b50611de589828a01611cf1565b9150509295509295509295565b80151581146112bc57600080fd5b60008060408385031215611e1357600080fd5b611e1c83611a99565b91506020830135611e2c81611df2565b809150509250929050565b60008060008060808587031215611e4d57600080fd5b611e5685611a99565b9350611e6460208601611a99565b925060408501359150606085013567ffffffffffffffff811115611e8757600080fd5b8501601f81018713611e9857600080fd5b611ea787823560208401611c7b565b91505092959194509250565b602080825282518282018190526000919060409081850190868401855b82811015611f1657815180516001600160a01b03908116865287820151168786015285810151868601526060908101519085015260809093019290850190600101611ed0565b5091979650505050505050565b60008060408385031215611f3657600080fd5b611f3f83611a99565b9150611f4d60208401611a99565b90509250929050565b602080825260129082015271151bdad95b881251081b9bdd08199bdd5b9960721b604082015260600190565b600181811c90821680611f9657607f821691505b602082108103611fb657634e487b7160e01b600052602260045260246000fd5b50919050565b6020808252602d908201527f4552433732313a2063616c6c6572206973206e6f7420746f6b656e206f776e6560408201526c1c881bdc88185c1c1c9bdd9959609a1b606082015260800190565b634e487b7160e01b600052603260045260246000fd5b60006001820161203f57634e487b7160e01b600052601160045260246000fd5b5060010190565b601f8211156105da57600081815260208120601f850160051c8101602086101561206d5750805b601f850160051c820191505b8181101561208c57828155600101612079565b505050505050565b815167ffffffffffffffff8111156120ae576120ae611c65565b6120c2816120bc8454611f82565b84612046565b602080601f8311600181146120f757600084156120df5750858301515b600019600386901b1c1916600185901b17855561208c565b600085815260208120601f198616915b8281101561212657888601518255948401946001909101908401612107565b50858210156121445787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b60008351612166818460208801611a36565b83519083019061217a818360208801611a36565b01949350505050565b60006020828403121561219557600080fd5b5051919050565b6000602082840312156121ae57600080fd5b8151610e9a81611df2565b60208082526025908201527f4552433732313a207472616e736665722066726f6d20696e636f72726563742060408201526437bbb732b960d91b606082015260800190565b60208082526032908201527f4552433732313a207472616e7366657220746f206e6f6e20455243373231526560408201527131b2b4bb32b91034b6b83632b6b2b73a32b960711b606082015260800190565b6001600160a01b038581168252841660208201526040810183905260806060820181905260009061228390830184611a5a565b9695505050505050565b60006020828403121561229f57600080fd5b8151610e9a81611a0356fea2646970667358221220a89e03bf32f38b15cc347ed6cd3fda4677d538a347fbd006ac8eec8414a683eb64736f6c63430008130033";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BUYMUSICNFT = "buyMusicNFT";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_GETMUSICNFTDATA = "getMusicNFTData";

    public static final String FUNC_GETSALEHISTORY = "getSaleHistory";

    public static final String FUNC_GETTOKENSALEINFO = "getTokenSaleInfo";

    public static final String FUNC_GETTOKENSOFOWNER = "getTokensOfOwner";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_MINTMUSICNFT = "mintMusicNFT";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_safeTransferFrom = "safeTransferFrom";

    public static final String FUNC_SELLMUSICNFT = "sellMusicNFT";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOKENURI = "tokenURI";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event LOGEVENT_EVENT = new Event("LogEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected NFT_sol_MusicNFT(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NFT_sol_MusicNFT(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NFT_sol_MusicNFT(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NFT_sol_MusicNFT(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public static List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public static List<LogEventEventResponse> getLogEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOGEVENT_EVENT, transactionReceipt);
        ArrayList<LogEventEventResponse> responses = new ArrayList<LogEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogEventEventResponse typedResponse = new LogEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.msgSender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.logMsg = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogEventEventResponse> logEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogEventEventResponse>() {
            @Override
            public LogEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGEVENT_EVENT, log);
                LogEventEventResponse typedResponse = new LogEventEventResponse();
                typedResponse.log = log;
                typedResponse.msgSender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.logMsg = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogEventEventResponse> logEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGEVENT_EVENT));
        return logEventEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String owner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new Address(160, owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> buyMusicNFT(BigInteger tokenId, BigInteger payment) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUYMUSICNFT, 
                Arrays.<Type>asList(new Uint256(tokenId),
                new Uint256(payment)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAPPROVED, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<MusicNFTData> getMusicNFTData(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMUSICNFTDATA, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<MusicNFTData>() {}));
        return executeRemoteCallSingleValueReturn(function, MusicNFTData.class);
    }

    public RemoteFunctionCall<List> getSaleHistory(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSALEHISTORY, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<SaleHistory>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<SaleInfo> getTokenSaleInfo(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOKENSALEINFO, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<SaleInfo>() {}));
        return executeRemoteCallSingleValueReturn(function, SaleInfo.class);
    }

    public RemoteFunctionCall<List> getTokensOfOwner(String owner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOKENSOFOWNER, 
                Arrays.<Type>asList(new Address(160, owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<MusicNFTMetaData>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String owner, String operator) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new Address(160, owner),
                new Address(160, operator)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mintMusicNFT(String to, String coverImageUrl, String creatorNickname, String trackTitle, String nftImageUrl, String combination) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MINTMUSICNFT, 
                Arrays.<Type>asList(new Address(160, to),
                new Utf8String(coverImageUrl),
                new Utf8String(creatorNickname),
                new Utf8String(trackTitle),
                new Utf8String(nftImageUrl),
                new Utf8String(combination)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNEROF, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId),
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> sellMusicNFT(BigInteger tokenId, BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SELLMUSICNFT, 
                Arrays.<Type>asList(new Uint256(tokenId),
                new Uint256(price)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator, Boolean approved) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new Address(160, operator),
                new Bool(approved)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> tokenURI(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENURI, 
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, to),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static NFT_sol_MusicNFT load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFT_sol_MusicNFT(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NFT_sol_MusicNFT load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFT_sol_MusicNFT(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NFT_sol_MusicNFT load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NFT_sol_MusicNFT(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NFT_sol_MusicNFT load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NFT_sol_MusicNFT(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NFT_sol_MusicNFT> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String katTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, katTokenAddress)));
        return deployRemoteCall(NFT_sol_MusicNFT.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<NFT_sol_MusicNFT> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String katTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, katTokenAddress)));
        return deployRemoteCall(NFT_sol_MusicNFT.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NFT_sol_MusicNFT> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String katTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, katTokenAddress)));
        return deployRemoteCall(NFT_sol_MusicNFT.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NFT_sol_MusicNFT> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String katTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, katTokenAddress)));
        return deployRemoteCall(NFT_sol_MusicNFT.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class MusicNFTData extends DynamicStruct {
        public String nftImageUrl;

        public String coverImageUrl;

        public String creatorNickname;

        public BigInteger createdDate;

        public String trackTitle;

        public String combination;

        public MusicNFTData(String nftImageUrl, String coverImageUrl, String creatorNickname, BigInteger createdDate, String trackTitle, String combination) {
            super(new Utf8String(nftImageUrl),
                    new Utf8String(coverImageUrl),
                    new Utf8String(creatorNickname),
                    new Uint256(createdDate),
                    new Utf8String(trackTitle),
                    new Utf8String(combination));
            this.nftImageUrl = nftImageUrl;
            this.coverImageUrl = coverImageUrl;
            this.creatorNickname = creatorNickname;
            this.createdDate = createdDate;
            this.trackTitle = trackTitle;
            this.combination = combination;
        }

        public MusicNFTData(Utf8String nftImageUrl, Utf8String coverImageUrl, Utf8String creatorNickname, Uint256 createdDate, Utf8String trackTitle, Utf8String combination) {
            super(nftImageUrl, coverImageUrl, creatorNickname, createdDate, trackTitle, combination);
            this.nftImageUrl = nftImageUrl.getValue();
            this.coverImageUrl = coverImageUrl.getValue();
            this.creatorNickname = creatorNickname.getValue();
            this.createdDate = createdDate.getValue();
            this.trackTitle = trackTitle.getValue();
            this.combination = combination.getValue();
        }
    }

    public static class SaleHistory extends StaticStruct {
        public String seller;

        public String buyer;

        public BigInteger price;

        public BigInteger timestamp;

        public SaleHistory(String seller, String buyer, BigInteger price, BigInteger timestamp) {
            super(new Address(160, seller),
                    new Address(160, buyer),
                    new Uint256(price),
                    new Uint256(timestamp));
            this.seller = seller;
            this.buyer = buyer;
            this.price = price;
            this.timestamp = timestamp;
        }

        public SaleHistory(Address seller, Address buyer, Uint256 price, Uint256 timestamp) {
            super(seller, buyer, price, timestamp);
            this.seller = seller.getValue();
            this.buyer = buyer.getValue();
            this.price = price.getValue();
            this.timestamp = timestamp.getValue();
        }
    }

    public static class SaleInfo extends StaticStruct {
        public Boolean isSelling;

        public BigInteger price;

        public SaleInfo(Boolean isSelling, BigInteger price) {
            super(new Bool(isSelling),
                    new Uint256(price));
            this.isSelling = isSelling;
            this.price = price;
        }

        public SaleInfo(Bool isSelling, Uint256 price) {
            super(isSelling, price);
            this.isSelling = isSelling.getValue();
            this.price = price.getValue();
        }
    }

    public static class MusicNFTMetaData extends DynamicStruct {
        public String nftImageUrl;

        public BigInteger tokenId;

        public MusicNFTMetaData(String nftImageUrl, BigInteger tokenId) {
            super(new Utf8String(nftImageUrl),
                    new Uint256(tokenId));
            this.nftImageUrl = nftImageUrl;
            this.tokenId = tokenId;
        }

        public MusicNFTMetaData(Utf8String nftImageUrl, Uint256 tokenId) {
            super(nftImageUrl, tokenId);
            this.nftImageUrl = nftImageUrl.getValue();
            this.tokenId = tokenId.getValue();
        }
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
    }

    public static class LogEventEventResponse extends BaseEventResponse {
        public String msgSender;

        public String logMsg;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger tokenId;
    }
}
