package blockchaindbcreator.service.exceptions;

public class MigrationFailedException extends RuntimeException {

    private static final String MIGRATION_FAILED = STR."Migration failed due to: %s";

    public MigrationFailedException(Throwable cause) {
        super(String.format(MIGRATION_FAILED, cause.getMessage()), cause);
    }
}
