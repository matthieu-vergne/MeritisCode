Here's a minimal viable program in Java using Hibernate to create a bank account, add and remove amounts from it, and retrieve the current amount it contains:

```java
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class BankAccount {
    private int id;
    private double amount;
    
    public BankAccount() {}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}

public class BankAccountDAO {
    private SessionFactory sessionFactory;
    
    public BankAccountDAO() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }
    
    public void createBankAccount(double amount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAmount(amount);
            
            session.save(bankAccount);
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public BankAccount getBankAccount(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        BankAccount bankAccount = null;
        
        try {
            transaction = session.beginTransaction();
            
            bankAccount = (BankAccount)session.get(BankAccount.class, id);
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        
        return bankAccount;
    }
    
    public void updateBankAccountAmount(int id, double amount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            BankAccount bankAccount = (BankAccount)session.get(BankAccount.class, id);
            bankAccount.setAmount(amount);
            
            session.update(bankAccount);
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}
```

To use this program, you'll need to have Hibernate and its dependencies installed in your project. You can then create a `BankAccountDAO` object and call the methods provided by it to interact with your bank account