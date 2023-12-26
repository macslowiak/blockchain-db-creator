package blockchaindbcreator.data.bnbsc.repository;

import blockchaindbcreator.data.bnbsc.entity.BnbSmartChainTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BnbScTransactionRepository extends JpaRepository<BnbSmartChainTransaction, String> {
}
