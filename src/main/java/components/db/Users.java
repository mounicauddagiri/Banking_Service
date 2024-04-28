package components.db;

public class Users {

    public int id;

    public String currency;

    public float amount;

    public Users(){

    }

    public Users(int i, float v, String usd) {
        this.id = i;
        this.amount = v;
        this.currency = usd;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }
    public float getAmount(){
        return this.amount;
    }

    public String getCurrency(){
        return this.currency;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
