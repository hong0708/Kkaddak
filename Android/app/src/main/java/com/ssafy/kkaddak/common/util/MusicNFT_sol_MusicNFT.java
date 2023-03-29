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
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple6;
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
public class MusicNFT_sol_MusicNFT extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b50604051620023f5380380620023f58339810160408190526200003491620000bd565b60405180604001604052806008815260200167135d5cda58d3919560c21b815250604051806040016040528060058152602001644d5553494360d81b815250816000908162000084919062000194565b50600162000093828262000194565b5050600780546001600160a01b0319166001600160a01b0393909316929092179091555062000260565b600060208284031215620000d057600080fd5b81516001600160a01b0381168114620000e857600080fd5b9392505050565b634e487b7160e01b600052604160045260246000fd5b600181811c908216806200011a57607f821691505b6020821081036200013b57634e487b7160e01b600052602260045260246000fd5b50919050565b601f8211156200018f57600081815260208120601f850160051c810160208610156200016a5750805b601f850160051c820191505b818110156200018b5782815560010162000176565b5050505b505050565b81516001600160401b03811115620001b057620001b0620000ef565b620001c881620001c1845462000105565b8462000141565b602080601f831160018114620002005760008415620001e75750858301515b600019600386901b1c1916600185901b1785556200018b565b600085815260208120601f198616915b82811015620002315788860151825594840194600190910190840162000210565b5085821015620002505787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b61218580620002706000396000f3fe608060405234801561001057600080fd5b50600436106101425760003560e01c8063846697f5116100b8578063b88d4fde1161007c578063b88d4fde146102fb578063c7adf7b21461030e578063c87b56dd1461034c578063e05d541d1461035f578063e985e9c51461037f578063f2c39154146103bb57600080fd5b8063846697f51461027657806389c077861461028957806395d89b411461029c578063a22cb465146102a4578063a7d7c7f3146102b757600080fd5b8063349c96521161010a578063349c9652146101d757806342842e0e146101ea5780635de6dc55146101fd5780636352211e1461021d5780636914db601461023057806370a082311461025557600080fd5b806301ffc9a71461014757806306fdde031461016f578063081812fc14610184578063095ea7b3146101af57806323b872dd146101c4575b600080fd5b61015a610155366004611926565b6103ce565b60405190151581526020015b60405180910390f35b610177610420565b6040516101669190611993565b6101976101923660046119a6565b6104b2565b6040516001600160a01b039091168152602001610166565b6101c26101bd3660046119db565b6104d9565b005b6101c26101d2366004611a05565b6105f3565b6101c26101e5366004611a41565b610624565b6101c26101f8366004611a05565b6106c2565b61021061020b366004611a63565b6106dd565b6040516101669190611a7e565b61019761022b3660046119a6565b6107bc565b61024361023e3660046119a6565b61081c565b60405161016696959493929190611ac2565b610268610263366004611a63565b610af8565b604051908152602001610166565b610268610284366004611be2565b610b7e565b600754610197906001600160a01b031681565b610177610c9d565b6101c26102b2366004611cd1565b610cac565b6102e46102c53660046119a6565b600a602052600090815260409020805460019091015460ff9091169082565b604080519215158352602083019190915201610166565b6101c2610309366004611d08565b610cbb565b61032161031c366004611a41565b610cf3565b604080516001600160a01b039586168152949093166020850152918301526060820152608001610166565b61017761035a3660046119a6565b610d45565b61037261036d3660046119a6565b610db9565b6040516101669190611d84565b61015a61038d366004611df4565b6001600160a01b03918216600090815260056020908152604080832093909416825291909152205460ff1690565b6101c26103c9366004611a41565b610e9b565b60006001600160e01b031982166380ac58cd60e01b14806103ff57506001600160e01b03198216635b5e139f60e01b145b8061041a57506301ffc9a760e01b6001600160e01b03198316145b92915050565b60606000805461042f90611e27565b80601f016020809104026020016040519081016040528092919081815260200182805461045b90611e27565b80156104a85780601f1061047d576101008083540402835291602001916104a8565b820191906000526020600020905b81548152906001019060200180831161048b57829003601f168201915b5050505050905090565b60006104bd82611175565b506000908152600460205260409020546001600160a01b031690565b60006104e4826107bc565b9050806001600160a01b0316836001600160a01b0316036105565760405162461bcd60e51b815260206004820152602160248201527f4552433732313a20617070726f76616c20746f2063757272656e74206f776e656044820152603960f91b60648201526084015b60405180910390fd5b336001600160a01b03821614806105725750610572813361038d565b6105e45760405162461bcd60e51b815260206004820152603d60248201527f4552433732313a20617070726f76652063616c6c6572206973206e6f7420746f60448201527f6b656e206f776e6572206f7220617070726f76656420666f7220616c6c000000606482015260840161054d565b6105ee83836111c8565b505050565b6105fd3382611236565b6106195760405162461bcd60e51b815260040161054d90611e61565b6105ee8383836112b5565b61062d826107bc565b6001600160a01b0316336001600160a01b0316146106a25760405162461bcd60e51b815260206004820152602c60248201527f596f7520646f206e6f742068617665207065726d697373696f6e20666f72207460448201526b3430ba103932b8bab2b9ba1760a11b606482015260840161054d565b6000918252600a6020526040909120805460ff1916600190811782550155565b6105ee83838360405180602001604052806000815250610cbb565b606060006106ea83610af8565b905060008167ffffffffffffffff81111561070757610707611b36565b604051908082528060200260200182016040528015610730578160200160208202803683370190505b509050600060015b60065481116107b25761074a81611419565b801561076f5750856001600160a01b0316610764826107bc565b6001600160a01b0316145b156107a0578083838151811061078757610787611eae565b60209081029190910101528161079c81611ec4565b9250505b806107aa81611ec4565b915050610738565b5090949350505050565b6000818152600260205260408120546001600160a01b03168061041a5760405162461bcd60e51b8152602060048201526018602482015277115490cdcc8c4e881a5b9d985b1a59081d1bdad95b88125160421b604482015260640161054d565b60086020526000908152604090208054819061083790611e27565b80601f016020809104026020016040519081016040528092919081815260200182805461086390611e27565b80156108b05780601f10610885576101008083540402835291602001916108b0565b820191906000526020600020905b81548152906001019060200180831161089357829003601f168201915b5050505050908060010180546108c590611e27565b80601f01602080910402602001604051908101604052809291908181526020018280546108f190611e27565b801561093e5780601f106109135761010080835404028352916020019161093e565b820191906000526020600020905b81548152906001019060200180831161092157829003601f168201915b50505050509080600201805461095390611e27565b80601f016020809104026020016040519081016040528092919081815260200182805461097f90611e27565b80156109cc5780601f106109a1576101008083540402835291602001916109cc565b820191906000526020600020905b8154815290600101906020018083116109af57829003601f168201915b5050505050908060030154908060040180546109e790611e27565b80601f0160208091040260200160405190810160405280929190818152602001828054610a1390611e27565b8015610a605780601f10610a3557610100808354040283529160200191610a60565b820191906000526020600020905b815481529060010190602001808311610a4357829003601f168201915b505050505090806005018054610a7590611e27565b80601f0160208091040260200160405190810160405280929190818152602001828054610aa190611e27565b8015610aee5780601f10610ac357610100808354040283529160200191610aee565b820191906000526020600020905b815481529060010190602001808311610ad157829003601f168201915b5050505050905086565b60006001600160a01b038216610b625760405162461bcd60e51b815260206004820152602960248201527f4552433732313a2061646472657373207a65726f206973206e6f7420612076616044820152683634b21037bbb732b960b91b606482015260840161054d565b506001600160a01b031660009081526003602052604090205490565b6000610b8e600680546001019055565b6000610b9960065490565b6040805160c08101825286815260208082018b90528183018a90524260608301526080820189905260a082018790528251808401845260008082528183018190528581526008909252929020815193945090928391908190610bfb9082611f39565b5060208201516001820190610c109082611f39565b5060408201516002820190610c259082611f39565b506060820151600382015560808201516004820190610c449082611f39565b5060a08201516005820190610c599082611f39565b5050506000838152600a602090815260409091208251815460ff191690151517815590820151600190910155610c8f8a84611436565b509098975050505050505050565b60606001805461042f90611e27565b610cb73383836115a3565b5050565b610cc53383611236565b610ce15760405162461bcd60e51b815260040161054d90611e61565b610ced84848484611671565b50505050565b60096020528160005260406000208181548110610d0f57600080fd5b600091825260209091206004909102018054600182015460028301546003909301546001600160a01b0392831695509116925084565b6060610d5082611175565b6000610d6760408051602081019091526000815290565b90506000815111610d875760405180602001604052806000815250610db2565b80610d91846116a4565b604051602001610da2929190611ff9565b6040516020818303038152906040525b9392505050565b6060610dc482611419565b610e055760405162461bcd60e51b8152602060048201526012602482015271151bdad95b881251081b9bdd08199bdd5b9960721b604482015260640161054d565b600082815260096020908152604080832080548251818502810185019093528083529193909284015b82821015610e90576000848152602090819020604080516080810182526004860290920180546001600160a01b039081168452600180830154909116848601526002820154928401929092526003015460608301529083529092019101610e2e565b505050509050919050565b610ea482611419565b610ee55760405162461bcd60e51b8152602060048201526012602482015271151bdad95b881251081b9bdd08199bdd5b9960721b604482015260640161054d565b6000828152600a60205260409020805460ff16610f335760405162461bcd60e51b815260206004820152600c60248201526b4e6f7420466f722053616c6560a01b604482015260640161054d565b8060010154821015610f7c5760405162461bcd60e51b8152602060048201526012602482015271139bdd08115b9bdd59da0814185e5b595b9d60721b604482015260640161054d565b6007546040516370a0823160e01b815233600482015283916001600160a01b0316906370a0823190602401602060405180830381865afa158015610fc4573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610fe89190612028565b101561102e5760405162461bcd60e51b81526020600482015260156024820152744e6f7420656e6f756768204b415420746f6b656e7360581b604482015260640161054d565b6000611039846107bc565b600754604051636abd941360e11b81523360048201526001600160a01b0380841660248301526044820187905260806064830152600360848301526213919560ea1b60a483015292935091169063d57b28269060c4016020604051808303816000875af11580156110ae573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906110d29190612041565b506110de8133866112b5565b815460ff1916909155604080516080810182526001600160a01b0392831681523360208083019182528284019586524260608401908152600097885260098252938720805460018181018355918952919097209251600490910290920180549285166001600160a01b0319938416178155905195810180549690941695909116949094179091559051600283015551600390910155565b61117e81611419565b6111c55760405162461bcd60e51b8152602060048201526018602482015277115490cdcc8c4e881a5b9d985b1a59081d1bdad95b88125160421b604482015260640161054d565b50565b600081815260046020526040902080546001600160a01b0319166001600160a01b03841690811790915581906111fd826107bc565b6001600160a01b03167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92560405160405180910390a45050565b600080611242836107bc565b9050806001600160a01b0316846001600160a01b0316148061128957506001600160a01b0380821660009081526005602090815260408083209388168352929052205460ff165b806112ad5750836001600160a01b03166112a2846104b2565b6001600160a01b0316145b949350505050565b826001600160a01b03166112c8826107bc565b6001600160a01b0316146112ee5760405162461bcd60e51b815260040161054d9061205e565b6001600160a01b0382166113505760405162461bcd60e51b8152602060048201526024808201527f4552433732313a207472616e7366657220746f20746865207a65726f206164646044820152637265737360e01b606482015260840161054d565b826001600160a01b0316611363826107bc565b6001600160a01b0316146113895760405162461bcd60e51b815260040161054d9061205e565b600081815260046020908152604080832080546001600160a01b03199081169091556001600160a01b0387811680865260038552838620805460001901905590871680865283862080546001019055868652600290945282852080549092168417909155905184937fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef91a4505050565b6000908152600260205260409020546001600160a01b0316151590565b6001600160a01b03821661148c5760405162461bcd60e51b815260206004820181905260248201527f4552433732313a206d696e7420746f20746865207a65726f2061646472657373604482015260640161054d565b61149581611419565b156114e25760405162461bcd60e51b815260206004820152601c60248201527f4552433732313a20746f6b656e20616c7265616479206d696e74656400000000604482015260640161054d565b6114eb81611419565b156115385760405162461bcd60e51b815260206004820152601c60248201527f4552433732313a20746f6b656e20616c7265616479206d696e74656400000000604482015260640161054d565b6001600160a01b038216600081815260036020908152604080832080546001019055848352600290915280822080546001600160a01b0319168417905551839291907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908290a45050565b816001600160a01b0316836001600160a01b0316036116045760405162461bcd60e51b815260206004820152601960248201527f4552433732313a20617070726f766520746f2063616c6c657200000000000000604482015260640161054d565b6001600160a01b03838116600081815260056020908152604080832094871680845294825291829020805460ff191686151590811790915591519182527f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a3505050565b61167c8484846112b5565b61168884848484611737565b610ced5760405162461bcd60e51b815260040161054d906120a3565b606060006116b183611838565b600101905060008167ffffffffffffffff8111156116d1576116d1611b36565b6040519080825280601f01601f1916602001820160405280156116fb576020820181803683370190505b5090508181016020015b600019016f181899199a1a9b1b9c1cb0b131b232b360811b600a86061a8153600a850494508461170557509392505050565b60006001600160a01b0384163b1561182d57604051630a85bd0160e11b81526001600160a01b0385169063150b7a029061177b9033908990889088906004016120f5565b6020604051808303816000875af19250505080156117b6575060408051601f3d908101601f191682019092526117b391810190612132565b60015b611813573d8080156117e4576040519150601f19603f3d011682016040523d82523d6000602084013e6117e9565b606091505b50805160000361180b5760405162461bcd60e51b815260040161054d906120a3565b805181602001fd5b6001600160e01b031916630a85bd0160e11b1490506112ad565b506001949350505050565b60008072184f03e93ff9f4daa797ed6e38ed64bf6a1f0160401b83106118775772184f03e93ff9f4daa797ed6e38ed64bf6a1f0160401b830492506040015b6d04ee2d6d415b85acef810000000083106118a3576d04ee2d6d415b85acef8100000000830492506020015b662386f26fc1000083106118c157662386f26fc10000830492506010015b6305f5e10083106118d9576305f5e100830492506008015b61271083106118ed57612710830492506004015b606483106118ff576064830492506002015b600a831061041a5760010192915050565b6001600160e01b0319811681146111c557600080fd5b60006020828403121561193857600080fd5b8135610db281611910565b60005b8381101561195e578181015183820152602001611946565b50506000910152565b6000815180845261197f816020860160208601611943565b601f01601f19169290920160200192915050565b602081526000610db26020830184611967565b6000602082840312156119b857600080fd5b5035919050565b80356001600160a01b03811681146119d657600080fd5b919050565b600080604083850312156119ee57600080fd5b6119f7836119bf565b946020939093013593505050565b600080600060608486031215611a1a57600080fd5b611a23846119bf565b9250611a31602085016119bf565b9150604084013590509250925092565b60008060408385031215611a5457600080fd5b50508035926020909101359150565b600060208284031215611a7557600080fd5b610db2826119bf565b6020808252825182820181905260009190848201906040850190845b81811015611ab657835183529284019291840191600101611a9a565b50909695505050505050565b60c081526000611ad560c0830189611967565b8281036020840152611ae78189611967565b90508281036040840152611afb8188611967565b90508560608401528281036080840152611b158186611967565b905082810360a0840152611b298185611967565b9998505050505050505050565b634e487b7160e01b600052604160045260246000fd5b600067ffffffffffffffff80841115611b6757611b67611b36565b604051601f8501601f19908116603f01168101908282118183101715611b8f57611b8f611b36565b81604052809350858152868686011115611ba857600080fd5b858560208301376000602087830101525050509392505050565b600082601f830112611bd357600080fd5b610db283833560208501611b4c565b60008060008060008060c08789031215611bfb57600080fd5b611c04876119bf565b9550602087013567ffffffffffffffff80821115611c2157600080fd5b611c2d8a838b01611bc2565b96506040890135915080821115611c4357600080fd5b611c4f8a838b01611bc2565b95506060890135915080821115611c6557600080fd5b611c718a838b01611bc2565b94506080890135915080821115611c8757600080fd5b611c938a838b01611bc2565b935060a0890135915080821115611ca957600080fd5b50611cb689828a01611bc2565b9150509295509295509295565b80151581146111c557600080fd5b60008060408385031215611ce457600080fd5b611ced836119bf565b91506020830135611cfd81611cc3565b809150509250929050565b60008060008060808587031215611d1e57600080fd5b611d27856119bf565b9350611d35602086016119bf565b925060408501359150606085013567ffffffffffffffff811115611d5857600080fd5b8501601f81018713611d6957600080fd5b611d7887823560208401611b4c565b91505092959194509250565b602080825282518282018190526000919060409081850190868401855b82811015611de757815180516001600160a01b03908116865287820151168786015285810151868601526060908101519085015260809093019290850190600101611da1565b5091979650505050505050565b60008060408385031215611e0757600080fd5b611e10836119bf565b9150611e1e602084016119bf565b90509250929050565b600181811c90821680611e3b57607f821691505b602082108103611e5b57634e487b7160e01b600052602260045260246000fd5b50919050565b6020808252602d908201527f4552433732313a2063616c6c6572206973206e6f7420746f6b656e206f776e6560408201526c1c881bdc88185c1c1c9bdd9959609a1b606082015260800190565b634e487b7160e01b600052603260045260246000fd5b600060018201611ee457634e487b7160e01b600052601160045260246000fd5b5060010190565b601f8211156105ee57600081815260208120601f850160051c81016020861015611f125750805b601f850160051c820191505b81811015611f3157828155600101611f1e565b505050505050565b815167ffffffffffffffff811115611f5357611f53611b36565b611f6781611f618454611e27565b84611eeb565b602080601f831160018114611f9c5760008415611f845750858301515b600019600386901b1c1916600185901b178555611f31565b600085815260208120601f198616915b82811015611fcb57888601518255948401946001909101908401611fac565b5085821015611fe95787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b6000835161200b818460208801611943565b83519083019061201f818360208801611943565b01949350505050565b60006020828403121561203a57600080fd5b5051919050565b60006020828403121561205357600080fd5b8151610db281611cc3565b60208082526025908201527f4552433732313a207472616e736665722066726f6d20696e636f72726563742060408201526437bbb732b960d91b606082015260800190565b60208082526032908201527f4552433732313a207472616e7366657220746f206e6f6e20455243373231526560408201527131b2b4bb32b91034b6b83632b6b2b73a32b960711b606082015260800190565b6001600160a01b038581168252841660208201526040810183905260806060820181905260009061212890830184611967565b9695505050505050565b60006020828403121561214457600080fd5b8151610db28161191056fea264697066735822122057781ca45d70bc7fdd2acea14cb4b25d07daea92333252ace6f687d5768518f664736f6c63430008130033";

    public static final String FUNC_NFTSALEINFO = "NFTSaleInfo";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BUYMUSICNFT = "buyMusicNFT";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_GETSALEHISTORY = "getSaleHistory";

    public static final String FUNC_GETTOKENSOFOWNER = "getTokensOfOwner";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_KATTOKEN = "katToken";

    public static final String FUNC_MINTMUSICNFT = "mintMusicNFT";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_safeTransferFrom = "safeTransferFrom";

    public static final String FUNC_SELLMUSICNFT = "sellMusicNFT";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOKENMETADATA = "tokenMetadata";

    public static final String FUNC_TOKENSALEHISTORIES = "tokenSaleHistories";

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
    protected MusicNFT_sol_MusicNFT(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MusicNFT_sol_MusicNFT(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MusicNFT_sol_MusicNFT(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MusicNFT_sol_MusicNFT(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteFunctionCall<Tuple2<Boolean, BigInteger>> NFTSaleInfo(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NFTSALEINFO, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<Boolean, BigInteger>>(function,
                new Callable<Tuple2<Boolean, BigInteger>>() {
                    @Override
                    public Tuple2<Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Boolean, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
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

    public RemoteFunctionCall<List> getTokensOfOwner(String owner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOKENSOFOWNER, 
                Arrays.<Type>asList(new Address(160, owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
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

    public RemoteFunctionCall<String> katToken() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_KATTOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteFunctionCall<Tuple6<String, String, String, BigInteger, String, String>> tokenMetadata(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENMETADATA, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple6<String, String, String, BigInteger, String, String>>(function,
                new Callable<Tuple6<String, String, String, BigInteger, String, String>>() {
                    @Override
                    public Tuple6<String, String, String, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, BigInteger, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<String, String, BigInteger, BigInteger>> tokenSaleHistories(BigInteger param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENSALEHISTORIES, 
                Arrays.<Type>asList(new Uint256(param0),
                new Uint256(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<String, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple4<String, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<String, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
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
    public static MusicNFT_sol_MusicNFT load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MusicNFT_sol_MusicNFT(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MusicNFT_sol_MusicNFT load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MusicNFT_sol_MusicNFT(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MusicNFT_sol_MusicNFT load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new MusicNFT_sol_MusicNFT(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MusicNFT_sol_MusicNFT load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MusicNFT_sol_MusicNFT(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MusicNFT_sol_MusicNFT> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String katTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, katTokenAddress)));
        return deployRemoteCall(MusicNFT_sol_MusicNFT.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<MusicNFT_sol_MusicNFT> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String katTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, katTokenAddress)));
        return deployRemoteCall(MusicNFT_sol_MusicNFT.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<MusicNFT_sol_MusicNFT> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String katTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, katTokenAddress)));
        return deployRemoteCall(MusicNFT_sol_MusicNFT.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<MusicNFT_sol_MusicNFT> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String katTokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, katTokenAddress)));
        return deployRemoteCall(MusicNFT_sol_MusicNFT.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
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
