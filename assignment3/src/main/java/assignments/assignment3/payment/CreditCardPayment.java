package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem {
    public final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    // fee transaction
    public long countTransactionFee(long amount){
        long fee = (long) (amount * TRANSACTION_FEE_PERCENTAGE);
        return fee;
    }
    // process payment, amount + fee
    public long processPayment(long amount){
        System.out.println("Berhasil Membayar Bill sebesar Rp " + amount + " dengan biaya transaksi sebesar Rp "+ countTransactionFee(amount));
        return amount + countTransactionFee(amount);
    }

}
