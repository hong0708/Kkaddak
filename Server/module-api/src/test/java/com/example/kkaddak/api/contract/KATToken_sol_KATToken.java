package com.example.kkaddak.api.contract;

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
    public static final String BINARY = "60806040523480156200001157600080fd5b50604051620014b3380380620014b38339810160408190526200003491620002af565b6004620000428482620003c3565b506005620000518382620003c3565b506006805460ff191660ff83169081179091556200008d9033906200007890600a620005a2565b6200008790620186a0620005b7565b62000096565b505050620005e7565b6001600160a01b038216620000f25760405162461bcd60e51b815260206004820152601f60248201527f45524332303a206d696e7420746f20746865207a65726f20616464726573730060448201526064015b60405180910390fd5b6007546200010190826200017e565b6007556001600160a01b0382166000908152602081905260409020546200012990826200017e565b6001600160a01b038316600081815260208181526040808320949094559251848152919290917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef910160405180910390a35050565b6000806200018d8385620005d1565b905083811015620001e15760405162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f7700000000006044820152606401620000e9565b90505b92915050565b634e487b7160e01b600052604160045260246000fd5b600082601f8301126200021257600080fd5b81516001600160401b03808211156200022f576200022f620001ea565b604051601f8301601f19908116603f011681019082821181831017156200025a576200025a620001ea565b816040528381526020925086838588010111156200027757600080fd5b600091505b838210156200029b57858201830151818301840152908201906200027c565b600093810190920192909252949350505050565b600080600060608486031215620002c557600080fd5b83516001600160401b0380821115620002dd57600080fd5b620002eb8783880162000200565b945060208601519150808211156200030257600080fd5b50620003118682870162000200565b925050604084015160ff811681146200032957600080fd5b809150509250925092565b600181811c908216806200034957607f821691505b6020821081036200036a57634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115620003be57600081815260208120601f850160051c81016020861015620003995750805b601f850160051c820191505b81811015620003ba57828155600101620003a5565b5050505b505050565b81516001600160401b03811115620003df57620003df620001ea565b620003f781620003f0845462000334565b8462000370565b602080601f8311600181146200042f5760008415620004165750858301515b600019600386901b1c1916600185901b178555620003ba565b600085815260208120601f198616915b8281101562000460578886015182559484019460019091019084016200043f565b50858210156200047f5787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b634e487b7160e01b600052601160045260246000fd5b600181815b80851115620004e6578160001904821115620004ca57620004ca6200048f565b80851615620004d857918102915b93841c9390800290620004aa565b509250929050565b600082620004ff57506001620001e4565b816200050e57506000620001e4565b8160018114620005275760028114620005325762000552565b6001915050620001e4565b60ff8411156200054657620005466200048f565b50506001821b620001e4565b5060208310610133831016604e8410600b841016171562000577575081810a620001e4565b620005838383620004a5565b80600019048211156200059a576200059a6200048f565b029392505050565b6000620005b08383620004ee565b9392505050565b8082028115828204841417620001e457620001e46200048f565b80820180821115620001e457620001e46200048f565b610ebc80620005f76000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c806370a082311161007157806370a082311461014757806395d89b4114610170578063a457c2d714610178578063a72732df1461018b578063d57b2826146101ab578063dd62ed3e146101be57600080fd5b806306fdde03146100b9578063095ea7b3146100d757806318160ddd146100fa57806323b872dd1461010c578063313ce5671461011f5780633950935114610134575b600080fd5b6100c16101f7565b6040516100ce9190610a8b565b60405180910390f35b6100ea6100e5366004610aba565b610289565b60405190151581526020016100ce565b6007545b6040519081526020016100ce565b6100ea61011a366004610ae4565b6102a0565b60065460405160ff90911681526020016100ce565b6100ea610142366004610aba565b610349565b6100fe610155366004610b20565b6001600160a01b031660009081526020819052604090205490565b6100c161037f565b6100ea610186366004610aba565b61038e565b61019e610199366004610b20565b6103c4565b6040516100ce9190610b3b565b6100ea6101b9366004610bf2565b61054d565b6100fe6101cc366004610cce565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b60606004805461020690610d01565b80601f016020809104026020016040519081016040528092919081815260200182805461023290610d01565b801561027f5780601f106102545761010080835404028352916020019161027f565b820191906000526020600020905b81548152906001019060200180831161026257829003601f168201915b5050505050905090565b6000610296338484610599565b5060015b92915050565b60006102d28484846040518060400160405280600c81526020016b7472616e7366657246726f6d60a01b8152506106bd565b6001600160a01b03841660009081526001602090815260408083203380855292529091205461030c9186916103079086610979565b610599565b6040513381527f7bf2655c5b27bca5462f1f973c316c9537b2da6083e494b623f3313ad847ec089060200160405180910390a15060019392505050565b3360008181526001602090815260408083206001600160a01b0387168452909152812054909161029691859061030790866109df565b60606005805461020690610d01565b3360008181526001602090815260408083206001600160a01b038716845290915281205490916102969185906103079086610979565b60606001600160a01b0382166104135760405162461bcd60e51b815260206004820152600f60248201526e696e76616c6964206164647265737360881b60448201526064015b60405180910390fd5b6001600160a01b038216600090815260026020908152604080832080548251818502810185019093528083529193909284015b828210156105425760008481526020908190206040805160a0810182526005860290920180546001600160a01b03908116845260018201541693830193909352600283015490820152600382015460608201526004820180549192916080840191906104b190610d01565b80601f01602080910402602001604051908101604052809291908181526020018280546104dd90610d01565b801561052a5780601f106104ff5761010080835404028352916020019161052a565b820191906000526020600020905b81548152906001019060200180831161050d57829003601f168201915b50505050508152505081526020019060010190610446565b505050509050919050565b600061055b858585856106bd565b6040513381527f7bf2655c5b27bca5462f1f973c316c9537b2da6083e494b623f3313ad847ec089060200160405180910390a1506001949350505050565b6001600160a01b0383166105fb5760405162461bcd60e51b8152602060048201526024808201527f45524332303a20617070726f76652066726f6d20746865207a65726f206164646044820152637265737360e01b606482015260840161040a565b6001600160a01b03821661065c5760405162461bcd60e51b815260206004820152602260248201527f45524332303a20617070726f766520746f20746865207a65726f206164647265604482015261737360f01b606482015260840161040a565b6001600160a01b0383811660008181526001602090815260408083209487168084529482529182902085905590518481527f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925910160405180910390a3505050565b6001600160a01b0384166107215760405162461bcd60e51b815260206004820152602560248201527f45524332303a207472616e736665722066726f6d20746865207a65726f206164604482015264647265737360d81b606482015260840161040a565b6001600160a01b0383166107835760405162461bcd60e51b815260206004820152602360248201527f45524332303a207472616e7366657220746f20746865207a65726f206164647260448201526265737360e81b606482015260840161040a565b6040805160a0810182526001600160a01b0380871680835290861660208084019190915242838501526060830186905260808301859052600091825281905291909120546107d19084610979565b6001600160a01b03808716600090815260208190526040808220939093559086168152205461080090846109df565b6001600160a01b03858116600090815260208181526040808320949094558883168252600280825284832080546001808201835591855293839020875160059095020180546001600160a01b0319908116958716959095178155928701519083018054909416941693909317909155918301519082015560608201516003820155608082015182919060048201906108989082610d8a565b5050506001600160a01b03808516600090815260026020818152604080842080546001818101835591865294839020875160059096020180549587166001600160a01b031996871617815592870151908301805491909616941693909317909355908301519082015560608201516003820155608082015182919060048201906109229082610d8a565b505050836001600160a01b0316856001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef8560405161096a91815260200190565b60405180910390a35050505050565b6000828211156109cb5760405162461bcd60e51b815260206004820152601e60248201527f536166654d6174683a207375627472616374696f6e206f766572666c6f770000604482015260640161040a565b60006109d78385610e60565b949350505050565b6000806109ec8385610e73565b905083811015610a3e5760405162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015260640161040a565b9392505050565b6000815180845260005b81811015610a6b57602081850181015186830182015201610a4f565b506000602082860101526020601f19601f83011685010191505092915050565b602081526000610a3e6020830184610a45565b80356001600160a01b0381168114610ab557600080fd5b919050565b60008060408385031215610acd57600080fd5b610ad683610a9e565b946020939093013593505050565b600080600060608486031215610af957600080fd5b610b0284610a9e565b9250610b1060208501610a9e565b9150604084013590509250925092565b600060208284031215610b3257600080fd5b610a3e82610a9e565b60006020808301818452808551808352604092508286019150828160051b87010184880160005b83811015610bce57888303603f19018552815180516001600160a01b03908116855288820151168885015286810151878501526060808201519085015260809081015160a091850182905290610bba81860183610a45565b968901969450505090860190600101610b62565b509098975050505050505050565b634e487b7160e01b600052604160045260246000fd5b60008060008060808587031215610c0857600080fd5b610c1185610a9e565b9350610c1f60208601610a9e565b925060408501359150606085013567ffffffffffffffff80821115610c4357600080fd5b818701915087601f830112610c5757600080fd5b813581811115610c6957610c69610bdc565b604051601f8201601f19908116603f01168101908382118183101715610c9157610c91610bdc565b816040528281528a6020848701011115610caa57600080fd5b82602086016020830137600060208483010152809550505050505092959194509250565b60008060408385031215610ce157600080fd5b610cea83610a9e565b9150610cf860208401610a9e565b90509250929050565b600181811c90821680610d1557607f821691505b602082108103610d3557634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115610d8557600081815260208120601f850160051c81016020861015610d625750805b601f850160051c820191505b81811015610d8157828155600101610d6e565b5050505b505050565b815167ffffffffffffffff811115610da457610da4610bdc565b610db881610db28454610d01565b84610d3b565b602080601f831160018114610ded5760008415610dd55750858301515b600019600386901b1c1916600185901b178555610d81565b600085815260208120601f198616915b82811015610e1c57888601518255948401946001909101908401610dfd565b5085821015610e3a5787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b634e487b7160e01b600052601160045260246000fd5b8181038181111561029a5761029a610e4a565b8082018082111561029a5761029a610e4a56fea2646970667358221220b4c9c31f8374e887e15d9d1976214713290e56de631294e3cc93486e3e35a5c864736f6c63430008130033";

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

    public RemoteFunctionCall<List> getTransferLog(String userAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTRANSFERLOG, 
                Arrays.<Type>asList(new Address(160, userAddress)),
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

    public RemoteFunctionCall<TransactionReceipt> transfer(String from, String recipient, BigInteger amount, String transferType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Address(160, from),
                new Address(160, recipient),
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
