package db.mongodb;

import java.text.ParseException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

public class MongoDBTableCreation {
  // Run as Java application to create MongoDB collections with index.
  public static void main(String[] args) throws ParseException {

		// Step 1, connection to MongoDB
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);

		// Step 2, remove old collection if there is any
		db.getCollection("users").drop();
		db.getCollection("items").drop();

		// Step 3, create new collections
		IndexOptions options = new IndexOptions().unique(true);
		db.getCollection("users").createIndex(new Document("user_id", 1), options);
		db.getCollection("items").createIndex(new Document("item_id", 1), options);

		// Step 4, insert fake user data and create index. admin/c0e024d9200b5705bc4804722636378a
		db.getCollection("users").insertOne(
				new Document().append("user_id", "admin")
				              .append("password", "c0e024d9200b5705bc4804722636378a")
						      .append("first_name", "VIP")
						      .append("last_name", "User"));

		mongoClient.close();
		System.out.println("Import is done successfully.");

  }
}
