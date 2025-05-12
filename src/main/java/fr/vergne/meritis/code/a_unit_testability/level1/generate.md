 Here's a simple example of how you can create a BankAccount class using Hibernate:

1. First, make sure you have Hibernate in your project by adding the following dependencies to your `pom.xml` file (if you're using Maven):

```xml
<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.4.21.Final</version> </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>1.4.200</version>
    </dependency>
</dependencies>
```

2. Create a `BankAccount` class representing the database table:

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

    private double amount;

    // Empty constructor
    public BankAccount() {}

    // Constructor with initial amount
    public BankAccount(double initialAmount) {
        this.amount = initialAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Methods to add and remove amounts from the account
    public void addAmount(double amountToAdd) {
        this.amount += amountToAdd;
    }

    public void removeAmount(double amountToRemove) {
        if (this.amount >= amountToRemove) {
            this.amount -= amountToRemove;
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}
```

3. Create a `HibernateUtil` class to configure Hibernate and manage the session:

```java
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
```

4. Create a `hibernate.cfg.xml` file in your resources directory with the following content:

```xml
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="dialect">org.hibernate.dialect.H2Dialect</property>
        <property name="connection.driver_class">org.h2.Driver</property>
        <property name="connection.url">jdbc:h2:./db/bank</property>
        <property name="connection.username"/>
        <property name="connection.password"/>
        <property name="show_sql">true</property>
        <mapping class="com.example.BankAccount"/>
    </session-factory>
</hibernate-configuration>
```

5. Create a `Main` class to test the BankAccount functionality:

```java
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            BankAccount account1 = new BankAccount(500);
            session.save(account1);

            // Add amount to the bank account
            account1.addAmount(100);

            // Retrieve the current amount in the bank account
            System.out.println("Current amount: " + account1.getAmount());

            // Remove amount from the bank account
            account1.removeAmount(250);

            // Retrieve the current amount in the bank account after removing
            System.out.println("Current amount: " + account1.getAmount());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
```

This is a minimal viable program to create a bank account, add and remove amounts from it, and retrieve the current amount it contains using Hibernate. Note that this example uses an in-memory H2 database for simplicity, but you can replace it with any other DBMS supported by Hibernate if needed