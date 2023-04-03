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
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
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
import org.web3j.tuples.generated.Tuple7;
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
public class Song_sol_MusicCopyRight extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610887806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c8063304cff30146100465780637adfa0d114610075578063931a889b14610096575b600080fd5b610059610054366004610440565b61009f565b60405161006c979695949392919061049f565b60405180910390f35b6100886100833660046105b8565b610304565b60405190815260200161006c565b61008860015481565b6000602081905290815260409020805481906100ba90610677565b80601f01602080910402602001604051908101604052809291908181526020018280546100e690610677565b80156101335780601f1061010857610100808354040283529160200191610133565b820191906000526020600020905b81548152906001019060200180831161011657829003601f168201915b50505050509080600101805461014890610677565b80601f016020809104026020016040519081016040528092919081815260200182805461017490610677565b80156101c15780601f10610196576101008083540402835291602001916101c1565b820191906000526020600020905b8154815290600101906020018083116101a457829003601f168201915b5050505050908060020180546101d690610677565b80601f016020809104026020016040519081016040528092919081815260200182805461020290610677565b801561024f5780601f106102245761010080835404028352916020019161024f565b820191906000526020600020905b81548152906001019060200180831161023257829003601f168201915b50505050509080600301805461026490610677565b80601f016020809104026020016040519081016040528092919081815260200182805461029090610677565b80156102dd5780601f106102b2576101008083540402835291602001916102dd565b820191906000526020600020905b8154815290600101906020018083116102c057829003601f168201915b5050505060048301546005840154600690940154929390929091506001600160a01b031687565b6001805460009182610315836106b1565b90915550506040805160e0810182528881526020808201899052818301889052606082018790526080820186905260a082018590523360c0830152600154600090815290819052919091208151819061036e9082610727565b50602082015160018201906103839082610727565b50604082015160028201906103989082610727565b50606082015160038201906103ad9082610727565b506080820151600482015560a0820151600582015560c090910151600690910180546001600160a01b0319166001600160a01b0390921691909117905560015460405133917f2783ebd5017fc78d62c4f568e7a45dbe9b048dc83615f46209141eff76386c0a9161042a91908b908b908b908b908b908b906107e7565b60405180910390a2506001549695505050505050565b60006020828403121561045257600080fd5b5035919050565b6000815180845260005b8181101561047f57602081850181015186830182015201610463565b506000602082860101526020601f19601f83011685010191505092915050565b60e0815260006104b260e083018a610459565b82810360208401526104c4818a610459565b905082810360408401526104d88189610459565b905082810360608401526104ec8188610459565b6080840196909652505060a08101929092526001600160a01b031660c090910152949350505050565b634e487b7160e01b600052604160045260246000fd5b600082601f83011261053c57600080fd5b813567ffffffffffffffff8082111561055757610557610515565b604051601f8301601f19908116603f0116810190828211818310171561057f5761057f610515565b8160405283815286602085880101111561059857600080fd5b836020870160208301376000602085830101528094505050505092915050565b60008060008060008060c087890312156105d157600080fd5b863567ffffffffffffffff808211156105e957600080fd5b6105f58a838b0161052b565b9750602089013591508082111561060b57600080fd5b6106178a838b0161052b565b9650604089013591508082111561062d57600080fd5b6106398a838b0161052b565b9550606089013591508082111561064f57600080fd5b5061065c89828a0161052b565b9350506080870135915060a087013590509295509295509295565b600181811c9082168061068b57607f821691505b6020821081036106ab57634e487b7160e01b600052602260045260246000fd5b50919050565b6000600182016106d157634e487b7160e01b600052601160045260246000fd5b5060010190565b601f82111561072257600081815260208120601f850160051c810160208610156106ff5750805b601f850160051c820191505b8181101561071e5782815560010161070b565b5050505b505050565b815167ffffffffffffffff81111561074157610741610515565b6107558161074f8454610677565b846106d8565b602080601f83116001811461078a57600084156107725750858301515b600019600386901b1c1916600185901b17855561071e565b600085815260208120601f198616915b828110156107b95788860151825594840194600190910190840161079a565b50858210156107d75787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b87815260e06020820152600061080060e0830189610459565b82810360408401526108128189610459565b905082810360608401526108268188610459565b9050828103608084015261083a8187610459565b60a0840195909552505060c001529594505050505056fea2646970667358221220f0d06da8d47b67247f2a1959de16f27a83f1611cb13244c68b2d588942634f2d64736f6c63430008130033";

    public static final String FUNC_REGISTERSONG = "registerSong";

    public static final String FUNC_SONGCOUNT = "songCount";

    public static final String FUNC_SONGS = "songs";

    public static final Event SONGREGISTERED_EVENT = new Event("SongRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected Song_sol_MusicCopyRight(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Song_sol_MusicCopyRight(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Song_sol_MusicCopyRight(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Song_sol_MusicCopyRight(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<SongRegisteredEventResponse> getSongRegisteredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SONGREGISTERED_EVENT, transactionReceipt);
        ArrayList<SongRegisteredEventResponse> responses = new ArrayList<SongRegisteredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SongRegisteredEventResponse typedResponse = new SongRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.songId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.title = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.artist = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.cover = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.songFile = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.uploadTime = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.combination = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SongRegisteredEventResponse> songRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SongRegisteredEventResponse>() {
            @Override
            public SongRegisteredEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SONGREGISTERED_EVENT, log);
                SongRegisteredEventResponse typedResponse = new SongRegisteredEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.songId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.title = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.artist = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.cover = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.songFile = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.uploadTime = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.combination = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SongRegisteredEventResponse> songRegisteredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SONGREGISTERED_EVENT));
        return songRegisteredEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> registerSong(String _title, String _artist, String _cover, String _songFile, BigInteger _uploadTime, BigInteger _combination) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERSONG, 
                Arrays.<Type>asList(new Utf8String(_title),
                new Utf8String(_artist),
                new Utf8String(_cover),
                new Utf8String(_songFile),
                new Uint256(_uploadTime),
                new Uint256(_combination)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> songCount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SONGCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<String, String, String, String, BigInteger, BigInteger, String>> songs(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SONGS, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple7<String, String, String, String, BigInteger, BigInteger, String>>(function,
                new Callable<Tuple7<String, String, String, String, BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple7<String, String, String, String, BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, String, String, String, BigInteger, BigInteger, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (String) results.get(6).getValue());
                    }
                });
    }

    @Deprecated
    public static Song_sol_MusicCopyRight load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Song_sol_MusicCopyRight(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Song_sol_MusicCopyRight load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Song_sol_MusicCopyRight(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Song_sol_MusicCopyRight load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Song_sol_MusicCopyRight(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Song_sol_MusicCopyRight load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Song_sol_MusicCopyRight(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Song_sol_MusicCopyRight> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Song_sol_MusicCopyRight.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Song_sol_MusicCopyRight> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Song_sol_MusicCopyRight.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Song_sol_MusicCopyRight> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Song_sol_MusicCopyRight.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Song_sol_MusicCopyRight> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Song_sol_MusicCopyRight.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class SongRegisteredEventResponse extends BaseEventResponse {
        public String owner;

        public BigInteger songId;

        public String title;

        public String artist;

        public String cover;

        public String songFile;

        public BigInteger uploadTime;

        public BigInteger combination;
    }
}
