package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem {
    public final double MINIMUM_TOTAL_PRICE = 50000;

    public long processPayment(long amount){
        // check if the amount is valid
        if(amount < MINIMUM_TOTAL_PRICE){
            throw new IllegalArgumentException("Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain\n");
        }
        System.out.println("Berhasil Membayar Bill sebesar Rp "+amount);
        return amount;
    }

}
