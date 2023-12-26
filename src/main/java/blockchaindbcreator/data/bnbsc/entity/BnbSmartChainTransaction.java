package blockchaindbcreator.data.bnbsc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class BnbSmartChainTransaction {

    @Id
    private String hash;

    private BigInteger nonce;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "block_number")
    private BigInteger blockNumber;

    @Column(name = "transaction_index")
    private BigInteger transactionIndex;

    @Column(name = "from_address")
    private String fromAddress;

    @Column(name = "to_address")
    private String toAddress;

    private String value;

    @Column(name = "gas_price")
    private BigInteger gasPrice;

    private BigInteger gas;

    private String input;

    private String creates;

    @Column(name = "public_key")
    private String publicKey;

    private String raw;

    private String r;

    private String s;

    private long v;
}
