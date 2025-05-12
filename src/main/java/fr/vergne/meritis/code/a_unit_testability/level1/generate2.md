Here is a basic example of how you might structure a BankAccount class using Hibernate for persisting data. This is a very simplified version. For real-world applications, more complex handling such as error checking (e.g., trying to withdraw more than is in the account), concurrency control and transaction management would be necessary.

Firstly, you need to create your BankAccount class:

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double balance;

    // Getters and setters...
}
```
Then, you'll need a Hibernate session factory for creating sessions to interact with the database:

```java
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
```
Finally, here's how you might use these classes to create a bank account, add and remove amounts from it, and retrieve its current amount:

```java
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        // Create a new bank account with an initial balance of 100
        BankAccount account = createAccount(100);

        // Deposit 50 into the account
        depositIntoAccount(account, 50);

        // Withdraw 25 from the account
        withdrawFromAccount(account, 25);

        // Print out the current balance of the account
        System.out.println("Current balance: " + getBalance(account));
    }

    private static BankAccount createAccount(double initialBalance) {
        BankAccount account = new BankAccount();
        account.setBalance(initialBalance);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(account);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return account;
    }

    private static void depositIntoAccount(BankAccount account, double amount) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            account = (BankAccount) session.get(BankAccount.class, account.getId());
            account.setBalance(account.getBalance() + amount);
            session.update(account);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void withdrawFromAccount(BankAccount account, double amount) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            account = (BankAccount) session.get(BankAccount.class, account.getId());
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                session.update(account);
            } else {
                System.out.println("Insufficient funds");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static double getBalance(BankAccount account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            account = (BankAccount) session.get(BankAccount.class, account.getId());
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return account.getBalance();
    }
}
