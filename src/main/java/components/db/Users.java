package components.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "users")
public class Users {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String currency;
    @DatabaseField
    private float amount;

    public Users(){

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
