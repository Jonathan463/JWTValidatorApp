import org.junit.jupiter.api.Test;
import org.validation.validator.JwtGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtGeneratorTest {

    @Test
    public void testValidJwtToken() {
        String secretKey = "yourSecretKey";
        long expirationMillis = 3600000; // 1 hour

        JwtGenerator jwtGenerator = new JwtGenerator(secretKey, expirationMillis);

        String subject = "testUser";
        String jwtToken = jwtGenerator.generateJwt(subject, expirationMillis, secretKey);

        assertEquals("verification pass", jwtGenerator.verifyJwt(jwtToken, secretKey));
    }

    @Test
    public void testInvalidJwtToken() {
        String secretKey = "yourSecretKey";
        long expirationMillis = 3600000; // 1 hour

        JwtGenerator jwtGenerator = new JwtGenerator(secretKey, expirationMillis);

        String invalidJwtToken = "invalidToken";

        assertEquals("verification fails", jwtGenerator.verifyJwt(invalidJwtToken, secretKey));
    }
}
