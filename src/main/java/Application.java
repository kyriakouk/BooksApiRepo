import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.specificvolume.SpecificVolume;
import exception.BookApiException;
import model.BookShelves.UserBookshelf;
import model.listBookshelf.ListVolumesBookshelf;
import model.specificvolume.VolumeInfo;
import model.thebookdb.BookResult;
import services.BookApiServices;
import services.BookApiServices.SearchHistory;

public class Application {
	public static void main(String[] args) throws BookApiException {
		Scanner scanner = new Scanner(System.in);
		BookApiServices api = new BookApiServices();
		SearchHistory searchHistory = new SearchHistory();
		System.out.println("welcome to BooksApi! ");
		System.out.println( );
		while (true) {
		
			System.out.println();
			System.out.println("Select one from the following options: ");
			System.out.println("1. Show books with a criteria.");
			System.out.println("2. Show info for this volume.");
			System.out.println("3. Show search history.");
			System.out.println("4. Retrieve list of user's bookshelves.");

			System.out.println("5. Retrieve contents of public user's bookshelves.");
			System.out.println("6. Exit.");
			System.out.println("Your choice: ");

			String input = scanner.nextLine();
			

			if (input.equals("1")) {
				System.out.println("Enter the book title(leave no space): ");
				String bookTitle = scanner.nextLine();
				System.out.println();

				System.out.println("Enter the book terms: ");
				System.out.println("The available terms are: ");
				System.out.println();
				System.out.println("intitle");
				System.out.println("inauthor");
				System.out.println("inpublisher");
				System.out.println("subject");
				System.out.println("isbn");
				System.out.println("lccn");
				System.out.println("oclc");
				System.out.println("Your choice: ");
				String terms = scanner.nextLine();
				System.out.println();
				

				System.out.println("Enter the book specificterms(name/surname of author for example): ");
				String specificterms = scanner.nextLine();
				System.out.println();
				try {
					BookResult result = api.GetVolumes(bookTitle, terms, specificterms);
					System.out.println(result.toString());
					searchHistory.addSearchQuery(bookTitle);
				} catch (BookApiException e) {
					System.err.println("Error occurred: " + e.getMessage());
				} catch (NumberFormatException e) {
					System.err.println("Invalid input");
				}
			} else if (input.equals("2")) {
				System.out.println("Enter the volume ID:");
				
				String volumeId = scanner.nextLine();
				System.out.println();
				try {
					SpecificVolume volumeInfo = api.getSpecificVolume(volumeId);
					System.out.println("title: " + volumeInfo.getVolumeInfo().getTitle());
					System.out.println("author: " + volumeInfo.getVolumeInfo().getAuthors());
					System.out.println("Description: " + volumeInfo.getVolumeInfo().getDescription());
					System.out.println("Publisher: " + volumeInfo.getVolumeInfo().getPublisher());
					System.out.println("publishedDate: " + volumeInfo.getVolumeInfo().getPublishedDate());
					System.out.println("pageCount: " + volumeInfo.getVolumeInfo().getPageCount());
					System.out.println("language: " + volumeInfo.getVolumeInfo().getLanguage());
					System.out.println("contentVersion: " + volumeInfo.getVolumeInfo().getContentVersion());
					System.out.println("categories: " + volumeInfo.getVolumeInfo().getCategories());

					searchHistory.addSearchQuery(volumeId);
				} catch (BookApiException e) {
					System.err.println("Error occurred: " + e.getMessage());
				}
			} else if (input.equals("3")) {
				List<String> searchHistoryList = searchHistory.getSearchHistory();
				System.out.println("Search history:");
				for (String searchQuery : searchHistoryList) {
					System.out.println(searchQuery);

				}
			} else if (input.equals("4")) {

				System.out.println("Enter the user ID:");
				String userId = scanner.nextLine();

				try {
					UserBookshelf BookshelvesResult = api.getBookshelves(userId);
					System.out.println("User bookshelves: ");
					for (int i = 0; i < BookshelvesResult.getItems().size(); i++) {
						System.out.println(BookshelvesResult.getItems().get(i));

					}
				} catch (BookApiException e) {
					System.err.println("Error occurred: " + e.getMessage());
				}
			} else if (input.equals("5")) {
			    System.out.println("Enter the user ID:");
			    String userId = scanner.nextLine();
			    System.out.println("Enter the bookshelf ID:");
			    String bookshelfId = scanner.nextLine();
			    if (bookshelfId.equals("1001")) {
			        try {
			            ListVolumesBookshelf volumeList = api.getVolumesFromBookshelf(userId, bookshelfId);
			            System.out.println("User bookshelves: ");
			            for (int i = 0; i < volumeList.getItems().size(); i++) {
			                System.out.println(volumeList.getItems().get(i));
			            }
			        } catch (BookApiException e) {
			            System.err.println("Error occurred: " + e.getMessage());
			        }
			    } else {
			        System.err.println("Invalid bookshelf ID or UserId.");
			    }
			} else if (input.equals("6")) {
				System.out.println("Exiting...");
				System.out.println("GoodBye!");
				scanner.close();
				System.exit(0);
			} else {
				System.out.println("Invalid input");
			}
		}

	}
}
