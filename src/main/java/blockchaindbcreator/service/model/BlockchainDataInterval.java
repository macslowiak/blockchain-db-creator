package blockchaindbcreator.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;

@AllArgsConstructor
@Getter
public class BlockchainDataInterval {
    private BigInteger fromBlockNumber;
    private BigInteger toBlockNumber;
}
