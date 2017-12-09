import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSAdecryption {

    public static void main(String[] args) {
        BigInteger ciphertext = new BigInteger("21");
        BigInteger n = new BigInteger("18444164967047483891");
        BigInteger e = new BigInteger("29");

        List<BigInteger> primes = findThePrime(n);

        BigInteger p = primes.get(0);
        BigInteger q = primes.get(1);

        BigInteger nPrime = nPrime(p, q);

//        BigInteger p = new BigInteger("4294404461");
//        BigInteger q = new BigInteger("4294929631");
//        BigInteger nPrime = new BigInteger("18444164958458149800");

        BigInteger d = extendedEucAlg(nPrime, e);
        BigInteger plaintext = ciphertext.modPow(d, n);

        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Plaintext: " + plaintext);
    }

    public static BigInteger nPrime(BigInteger p, BigInteger q) {
        BigInteger one = BigInteger.ONE;
        BigInteger pP = p.subtract(one);
        BigInteger qP = q.subtract(one);
        return pP.multiply(qP);
    }

    public static List<BigInteger> findThePrime(BigInteger n) {
        List<BigInteger> primes = new ArrayList<BigInteger>();
        BigInteger primeFactor = BigInteger.ZERO;

        for (BigInteger i = new BigInteger("2"); i
                .compareTo(n.divide(i)) <= 0;) {
            if (n.mod(i).longValue() == 0) {
                primeFactor = i;
                primes.add(primeFactor);
                n = n.divide(i);
            } else {
                i = i.add(BigInteger.ONE);
            }
        }

        if (primeFactor.compareTo(n) < 0) {
            primes.add(n);
        } else {
            primes.add(primeFactor);
        }
        return primes;
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
