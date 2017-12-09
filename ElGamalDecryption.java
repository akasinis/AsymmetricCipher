import java.math.BigInteger;

public class ElGamalDecryption {

    public static void main(String[] args) {
        BigInteger c1 = new BigInteger("187341129");
        BigInteger c2 = new BigInteger("881954783");

        BigInteger q = new BigInteger("1605333871");
        BigInteger a = new BigInteger("43");
        BigInteger YA = new BigInteger("22");

        BigInteger XA = findXA(YA, a, q);
        //BigInteger XA = new BigInteger("1463159957");

        BigInteger K = c1.modPow(XA, q);
        System.out.println("Key: " + K);

        BigInteger invK = extendedEucAlg(q, K);
        System.out.println("Inverse K: " + invK);

        BigInteger plaintext = (c2.multiply(invK)).remainder(q);
        System.out.println("Ciphertext one: " + c1);
        System.out.println("Ciphertext two: " + c2);
        System.out.println("Plaintext: " + plaintext);
    }

    public static BigInteger findXA(BigInteger ya, BigInteger a, BigInteger q) {
        BigInteger i = ya;
        BigInteger solution = a.modPow(i, q);
        while (true) {
            if (a.modPow(solution, q).equals(ya)) {
                break;
            }
            i = solution;
            solution = a.modPow(i, q);
        }
        return solution;
    }

    public static BigInteger squareMult(BigInteger exponent, BigInteger base,
            BigInteger modulo) {
        String byteExp = exponent.toString(2);
        BigInteger result = BigInteger.ONE;
        BigInteger two = new BigInteger("2");

        for (int i = byteExp.length() - 1; i > -1; i--) {
            if (byteExp.charAt(i) == '1') {
                BigInteger byteExpValue = two.pow(i);
                result = result.multiply(base.modPow(byteExpValue, modulo));
            }
        }
        return result.mod(modulo);
    }

    public static BigInteger extendedEucAlg(BigInteger m, BigInteger b) {
        BigInteger A1 = BigInteger.ONE;
        BigInteger A2 = BigInteger.ZERO;
        BigInteger A3 = m;

        BigInteger B1 = BigInteger.ZERO;
        BigInteger B2 = BigInteger.ONE;
        BigInteger B3 = b;

        while (!B3.equals(BigInteger.ZERO)) {

            if (B3.equals(BigInteger.ONE)) {
                return B2;
            }
            BigInteger q = A3.divide(B3);

            BigInteger T1 = A1.subtract(q.multiply(B1));
            BigInteger T2 = A2.subtract(q.multiply(B2));
            BigInteger T3 = A3.subtract(q.multiply(B3));

            A1 = B1;
            A2 = B2;
            A3 = B3;

            B1 = T1;
            B2 = T2;
            B3 = T3;
        }
        return A3;
    }
}
