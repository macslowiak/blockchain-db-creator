package blockchaindbcreator.service.mapper;

import blockchaindbcreator.data.bnbsc.entity.BnbSmartChainTransaction;
import org.mapstruct.Mapper;
import org.web3j.protocol.core.methods.response.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    BnbSmartChainTransaction transactionToBnbSmartChainTransaction(Transaction transaction);
}
