package program;

import models.Category;
import models.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Category category1 = new Category("Phones");
            Category category2 = new Category("Laptops");

            Product product1 = new Product("Phone1", "Some description of phone1", 1200, category1);
            Product product2 = new Product("Phone2", "Some description of phone2", 5000, category1);
            Product product3 = new Product("Phone2", "Some description of phone3", 3000, category1);

            Product product4 = new Product("Laptop1", "Some description of laptop1", 7200, category2);
            Product product5 = new Product("Laptop2", "Some description of laptop2", 9000, category2);
            Product product6 = new Product("Laptop3", "Some description of laptop3", 10000, category2);

            CreateCategory(category1, session);
            CreateCategory(category2, session);

            ShowCategories(session);

            CreateProduct(product1, session);
            CreateProduct(product2, session);
            CreateProduct(product3, session);
            CreateProduct(product4, session);
            CreateProduct(product5, session);
            CreateProduct(product6, session);

            ShowProducts(session);

            category1 = GetCategoryById(1, session);
            if (category1 != null) {
                category1.setName("Phones123");
                UpdateCategory(category1, session);
                ShowCategories(session);
            }

            product1 = GetProductById(1, session);
            if (product1 != null) {
                product1.setName("New laptop");
                product1.setDescription("New descrtiotion");
                product1.setPrice(1500);
                product1.setCategory(category2);
                UpdateProduct(product1, session);
            }
            ShowProducts(session);

            product3 = GetProductById(3, session);
            if (product3 != null)
                DeleteProduct(product3, session);
            ShowProducts(session);

        } catch (Exception ex) {
            if (ex.getMessage() != null)
                System.out.println(ex.getMessage());
            else
                System.out.println("Something went wrong.");
        } finally {
            if (session != null)
                session.close();
        }
    }

    public static void CreateCategory(Category category, Session session) {
        session.save(category);
        System.out.println("Category create successfully.");
    }

    public static void CreateProduct(Product product, Session session) {
        session.save(product);
        System.out.println("Product create successfully.");
    }

    public static void UpdateCategory(Category category, Session session) {
        session.beginTransaction();
        session.update(category);
        session.getTransaction().commit();
        System.out.println("Category update successfully.");
    }

    public static void UpdateProduct(Product product, Session session) {
        session.beginTransaction();
        session.update(product);
        session.getTransaction().commit();
        System.out.println("Product update successfully.");
    }

    public static Product GetProductById(int id, Session session) {
        return session.get(Product.class, id);
    }

    public static Category GetCategoryById(int id, Session session) {
        return session.find(Category.class, id);
    }

    public static void DeleteProduct(Product product, Session session) {
        session.beginTransaction();
        session.delete(product);
        session.getTransaction().commit();
        System.out.println("Product deleted successfully.");
    }

    public static void ShowCategories(Session session) {
        Query query = session.createQuery("FROM Category");
        List<Category> categories = query.list();
        System.out.println("Categories:");
        for (Category item : categories) {
            System.out.println(item);
        }
    }

    public static void ShowProducts(Session session) {
        Query query = session.createQuery("FROM Product");
        List<Product> products = query.list();

        System.out.println("Products:");
        for (Product item : products) {
            System.out.println(item);
        }
    }

}
