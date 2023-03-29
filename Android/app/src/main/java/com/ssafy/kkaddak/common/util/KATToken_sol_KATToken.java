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
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
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
public class KATToken_sol_KATToken extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162001445380380620014458339810160408190526200003491620002af565b6004620000428482620003c3565b506005620000518382620003c3565b506006805460ff191660ff83169081179091556200008d9033906200007890600a620005a2565b6200008790620186a0620005b7565b62000096565b505050620005e7565b6001600160a01b038216620000f25760405162461bcd60e51b815260206004820152601f60248201527f45524332303a206d696e7420746f20746865207a65726f20616464726573730060448201526064015b60405180910390fd5b6007546200010190826200017e565b6007556001600160a01b0382166000908152602081905260409020546200012990826200017e565b6001600160a01b038316600081815260208181526040808320949094559251848152919290917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef910160405180910390a35050565b6000806200018d8385620005d1565b905083811015620001e15760405162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f7700000000006044820152606401620000e9565b90505b92915050565b634e487b7160e01b600052604160045260246000fd5b600082601f8301126200021257600080fd5b81516001600160401b03808211156200022f576200022f620001ea565b604051601f8301601f19908116603f011681019082821181831017156200025a576200025a620001ea565b816040528381526020925086838588010111156200027757600080fd5b600091505b838210156200029b57858201830151818301840152908201906200027c565b600093810190920192909252949350505050565b600080600060608486031215620002c557600080fd5b83516001600160401b0380821115620002dd57600080fd5b620002eb8783880162000200565b945060208601519150808211156200030257600080fd5b50620003118682870162000200565b925050604084015160ff811681146200032957600080fd5b809150509250925092565b600181811c908216806200034957607f821691505b6020821081036200036a57634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115620003be57600081815260208120601f850160051c81016020861015620003995750805b601f850160051c820191505b81811015620003ba57828155600101620003a5565b5050505b505050565b81516001600160401b03811115620003df57620003df620001ea565b620003f781620003f0845462000334565b8462000370565b602080601f8311600181146200042f5760008415620004165750858301515b600019600386901b1c1916600185901b178555620003ba565b600085815260208120601f198616915b8281101562000460578886015182559484019460019091019084016200043f565b50858210156200047f5787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b634e487b7160e01b600052601160045260246000fd5b600181815b80851115620004e6578160001904821115620004ca57620004ca6200048f565b80851615620004d857918102915b93841c9390800290620004aa565b509250929050565b600082620004ff57506001620001e4565b816200050e57506000620001e4565b8160018114620005275760028114620005325762000552565b6001915050620001e4565b60ff8411156200054657620005466200048f565b50506001821b620001e4565b5060208310610133831016604e8410600b841016171562000577575081810a620001e4565b620005838383620004a5565b80600019048211156200059a576200059a6200048f565b029392505050565b6000620005b08383620004ee565b9392505050565b8082028115828204841417620001e457620001e46200048f565b80820180821115620001e457620001e46200048f565b610e4e80620005f76000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c80633950935111610071578063395093511461014957806356b8c7241461015c57806370a082311461016f57806395d89b4114610198578063a457c2d7146101a0578063dd62ed3e146101b357600080fd5b806302b7366e146100b957806306fdde03146100d7578063095ea7b3146100ec57806318160ddd1461010f57806323b872dd14610121578063313ce56714610134575b600080fd5b6100c16101ec565b6040516100ce9190610a2e565b60405180910390f35b6100df610361565b6040516100ce9190610acf565b6100ff6100fa366004610afe565b6103f3565b60405190151581526020016100ce565b6007545b6040519081526020016100ce565b6100ff61012f366004610b28565b61040a565b60065460405160ff90911681526020016100ce565b6100ff610157366004610afe565b6104b3565b6100ff61016a366004610b7a565b6104e9565b61011361017d366004610c45565b6001600160a01b031660009081526020819052604090205490565b6100df6104f7565b6100ff6101ae366004610afe565b610506565b6101136101c1366004610c60565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b6060336102325760405162461bcd60e51b815260206004820152600f60248201526e696e76616c6964206164647265737360881b60448201526064015b60405180910390fd5b33600090815260026020908152604080832080548251818502810185019093528083529193909284015b828210156103585760008481526020908190206040805160a0810182526005860290920180546001600160a01b03908116845260018201541693830193909352600283015490820152600382015460608201526004820180549192916080840191906102c790610c93565b80601f01602080910402602001604051908101604052809291908181526020018280546102f390610c93565b80156103405780601f1061031557610100808354040283529160200191610340565b820191906000526020600020905b81548152906001019060200180831161032357829003601f168201915b5050505050815250508152602001906001019061025c565b50505050905090565b60606004805461037090610c93565b80601f016020809104026020016040519081016040528092919081815260200182805461039c90610c93565b80156103e95780601f106103be576101008083540402835291602001916103e9565b820191906000526020600020905b8154815290600101906020018083116103cc57829003601f168201915b5050505050905090565b600061040033848461053c565b5060015b92915050565b600061043c8484846040518060400160405280600c81526020016b7472616e7366657246726f6d60a01b815250610660565b6001600160a01b038416600090815260016020908152604080832033808552925290912054610476918691610471908661091c565b61053c565b6040513381527f7bf2655c5b27bca5462f1f973c316c9537b2da6083e494b623f3313ad847ec089060200160405180910390a15060019392505050565b3360008181526001602090815260408083206001600160a01b038716845290915281205490916104009185906104719086610982565b600061047633858585610660565b60606005805461037090610c93565b3360008181526001602090815260408083206001600160a01b03871684529091528120549091610400918590610471908661091c565b6001600160a01b03831661059e5760405162461bcd60e51b8152602060048201526024808201527f45524332303a20617070726f76652066726f6d20746865207a65726f206164646044820152637265737360e01b6064820152608401610229565b6001600160a01b0382166105ff5760405162461bcd60e51b815260206004820152602260248201527f45524332303a20617070726f766520746f20746865207a65726f206164647265604482015261737360f01b6064820152608401610229565b6001600160a01b0383811660008181526001602090815260408083209487168084529482529182902085905590518481527f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925910160405180910390a3505050565b6001600160a01b0384166106c45760405162461bcd60e51b815260206004820152602560248201527f45524332303a207472616e736665722066726f6d20746865207a65726f206164604482015264647265737360d81b6064820152608401610229565b6001600160a01b0383166107265760405162461bcd60e51b815260206004820152602360248201527f45524332303a207472616e7366657220746f20746865207a65726f206164647260448201526265737360e81b6064820152608401610229565b6040805160a0810182526001600160a01b038087168083529086166020808401919091524283850152606083018690526080830185905260009182528190529190912054610774908461091c565b6001600160a01b0380871660009081526020819052604080822093909355908616815220546107a39084610982565b6001600160a01b03858116600090815260208181526040808320949094558883168252600280825284832080546001808201835591855293839020875160059095020180546001600160a01b03199081169587169590951781559287015190830180549094169416939093179091559183015190820155606082015160038201556080820151829190600482019061083b9082610d1c565b5050506001600160a01b03808516600090815260026020818152604080842080546001818101835591865294839020875160059096020180549587166001600160a01b031996871617815592870151908301805491909616941693909317909355908301519082015560608201516003820155608082015182919060048201906108c59082610d1c565b505050836001600160a01b0316856001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef8560405161090d91815260200190565b60405180910390a35050505050565b60008282111561096e5760405162461bcd60e51b815260206004820152601e60248201527f536166654d6174683a207375627472616374696f6e206f766572666c6f7700006044820152606401610229565b600061097a8385610df2565b949350505050565b60008061098f8385610e05565b9050838110156109e15760405162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f7700000000006044820152606401610229565b9392505050565b6000815180845260005b81811015610a0e576020818501810151868301820152016109f2565b506000602082860101526020601f19601f83011685010191505092915050565b60006020808301818452808551808352604092508286019150828160051b87010184880160005b83811015610ac157888303603f19018552815180516001600160a01b03908116855288820151168885015286810151878501526060808201519085015260809081015160a091850182905290610aad818601836109e8565b968901969450505090860190600101610a55565b509098975050505050505050565b6020815260006109e160208301846109e8565b80356001600160a01b0381168114610af957600080fd5b919050565b60008060408385031215610b1157600080fd5b610b1a83610ae2565b946020939093013593505050565b600080600060608486031215610b3d57600080fd5b610b4684610ae2565b9250610b5460208501610ae2565b9150604084013590509250925092565b634e487b7160e01b600052604160045260246000fd5b600080600060608486031215610b8f57600080fd5b610b9884610ae2565b925060208401359150604084013567ffffffffffffffff80821115610bbc57600080fd5b818601915086601f830112610bd057600080fd5b813581811115610be257610be2610b64565b604051601f8201601f19908116603f01168101908382118183101715610c0a57610c0a610b64565b81604052828152896020848701011115610c2357600080fd5b8260208601602083013760006020848301015280955050505050509250925092565b600060208284031215610c5757600080fd5b6109e182610ae2565b60008060408385031215610c7357600080fd5b610c7c83610ae2565b9150610c8a60208401610ae2565b90509250929050565b600181811c90821680610ca757607f821691505b602082108103610cc757634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115610d1757600081815260208120601f850160051c81016020861015610cf45750805b601f850160051c820191505b81811015610d1357828155600101610d00565b5050505b505050565b815167ffffffffffffffff811115610d3657610d36610b64565b610d4a81610d448454610c93565b84610ccd565b602080601f831160018114610d7f5760008415610d675750858301515b600019600386901b1c1916600185901b178555610d13565b600085815260208120601f198616915b82811015610dae57888601518255948401946001909101908401610d8f565b5085821015610dcc5787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b634e487b7160e01b600052601160045260246000fd5b8181038181111561040457610404610ddc565b8082018082111561040457610404610ddc56fea2646970667358221220dea026a31793ce834ce5fdc6da55e338418029f992ca7efbd841bd046e08ae5464736f6c63430008130033";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_DECREASEALLOWANCE = "decreaseAllowance";

    public static final String FUNC_GETTRANSFERLOG = "getTransferLog";

    public static final String FUNC_INCREASEALLOWANCE = "increaseAllowance";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event MSGSENDER_EVENT = new Event("MsgSender", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected KATToken_sol_KATToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected KATToken_sol_KATToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected KATToken_sol_KATToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected KATToken_sol_KATToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public static List<MsgSenderEventResponse> getMsgSenderEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MSGSENDER_EVENT, transactionReceipt);
        ArrayList<MsgSenderEventResponse> responses = new ArrayList<MsgSenderEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            MsgSenderEventResponse typedResponse = new MsgSenderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.msgSender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MsgSenderEventResponse> msgSenderEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, MsgSenderEventResponse>() {
            @Override
            public MsgSenderEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(MSGSENDER_EVENT, log);
                MsgSenderEventResponse typedResponse = new MsgSenderEventResponse();
                typedResponse.log = log;
                typedResponse.msgSender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MsgSenderEventResponse> msgSenderEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MSGSENDER_EVENT));
        return msgSenderEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> allowance(String owner, String spender) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new Address(160, owner),
                new Address(160, spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String spender, BigInteger value) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new Address(160, spender),
                new Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new Address(160, account)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> decreaseAllowance(String spender, BigInteger subtractedValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DECREASEALLOWANCE, 
                Arrays.<Type>asList(new Address(160, spender),
                new Uint256(subtractedValue)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getTransferLog() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTRANSFERLOG, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<TransferData>>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> increaseAllowance(String spender, BigInteger addedValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INCREASEALLOWANCE, 
                Arrays.<Type>asList(new Address(160, spender),
                new Uint256(addedValue)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String recipient, BigInteger amount, String transferType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Address(160, recipient),
                new Uint256(amount),
                new Utf8String(transferType)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String sender, String recipient, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new Address(160, sender),
                new Address(160, recipient),
                new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static KATToken_sol_KATToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new KATToken_sol_KATToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static KATToken_sol_KATToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new KATToken_sol_KATToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static KATToken_sol_KATToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new KATToken_sol_KATToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static KATToken_sol_KATToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new KATToken_sol_KATToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<KATToken_sol_KATToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String name, String symbol, BigInteger decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Utf8String(name),
                new Utf8String(symbol),
                new Uint8(decimals)));
        return deployRemoteCall(KATToken_sol_KATToken.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<KATToken_sol_KATToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String name, String symbol, BigInteger decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Utf8String(name),
                new Utf8String(symbol),
                new Uint8(decimals)));
        return deployRemoteCall(KATToken_sol_KATToken.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<KATToken_sol_KATToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String name, String symbol, BigInteger decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Utf8String(name),
                new Utf8String(symbol),
                new Uint8(decimals)));
        return deployRemoteCall(KATToken_sol_KATToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<KATToken_sol_KATToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String name, String symbol, BigInteger decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Utf8String(name),
                new Utf8String(symbol),
                new Uint8(decimals)));
        return deployRemoteCall(KATToken_sol_KATToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class TransferData extends DynamicStruct {
        public String sender;

        public String recipient;

        public BigInteger timeStamp;

        public BigInteger amount;

        public String transferType;

        public TransferData(String sender, String recipient, BigInteger timeStamp, BigInteger amount, String transferType) {
            super(new Address(160, sender),
                    new Address(160, recipient),
                    new Uint256(timeStamp),
                    new Uint256(amount),
                    new Utf8String(transferType));
            this.sender = sender;
            this.recipient = recipient;
            this.timeStamp = timeStamp;
            this.amount = amount;
            this.transferType = transferType;
        }

        public TransferData(Address sender, Address recipient, Uint256 timeStamp, Uint256 amount, Utf8String transferType) {
            super(sender, recipient, timeStamp, amount, transferType);
            this.sender = sender.getValue();
            this.recipient = recipient.getValue();
            this.timeStamp = timeStamp.getValue();
            this.amount = amount.getValue();
            this.transferType = transferType.getValue();
        }
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String spender;

        public BigInteger value;
    }

    public static class MsgSenderEventResponse extends BaseEventResponse {
        public String msgSender;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }
}
