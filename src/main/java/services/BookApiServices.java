package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.BookApiException;
import model.BooksInfo;
import model.BookShelves.UserBookshelf;
import model.SpecificBookShelves.SpecificPublicBookshelf;
import model.listBookshelf.ListVolumesBookshelf;
import model.specificvolume.SpecificVolume;
import model.specificvolume.VolumeInfo;
import model.thebookdb.BookResult;
import model.thebookdb.ErrorResponse;
import model.thebookdb.Item;

public class BookApiServices {

	private final String API_URL;
	private final String API_KEY;
	private static List<String> searchHistory = new ArrayList<>();

	public BookApiServices() {
		this.API_URL = "https://www.googleapis.com";
		this.API_KEY = "your api key";

	}

	// METHOD_1 https://www.googleapis.com/books/v1/volumes?q=search+terms

	public BookResult GetVolumes(String bookName, String terms, String SpecificTerms) throws BookApiException {

		String basicuri = "https://www.googleapis.com/books/v1/volumes?q=";
		String uri = basicuri + bookName;
		
		if (!terms.equals(null) || !terms.equals("")) {
			uri = uri + "+" + terms + ":" + SpecificTerms;
		}
		uri = uri + "&key=" + API_KEY;
		final HttpGet getRequest = new HttpGet(uri);
		final CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

			CloseableHttpResponse response = httpclient.execute(getRequest);
			final HttpEntity entity = response.getEntity();
			final ObjectMapper mapper = new ObjectMapper();

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				ErrorResponse errorResponse = mapper.readValue(entity.getContent(), ErrorResponse.class);
				if (errorResponse.getStatusMessage() != null) {
					throw new BookApiException("Error occurred on API call: " + errorResponse.getStatusMessage());
				}
			}
			final BookResult BookResult = mapper.readValue(entity.getContent(), BookResult.class);
			return BookResult;

		} catch (Exception e) {
			throw new BookApiException("Error occurred on API call: " + e.getMessage());
		}

	}

	// METHOD_2
	// https://www.googleapis.com/books/v1/volumes/zyTCAlFPjgYC?key=yourAPIKey

	// https://www.googleapis.com/books/v1/volumes/volumeId
	/*
	 * public VolumeInfo getSpecificVolume(String volumeId) throws BookApiException
	 * { String BasicUri = "https://www.googleapis.com/books/v1/volumes/"; String
	 * uri =BasicUri + volumeId + "?key=" + API_KEY; final HttpGet getRequest = new
	 * HttpGet(uri);
	 */
	public SpecificVolume getSpecificVolume(String volumeId) throws BookApiException {
		String BasicUri = "https://www.googleapis.com/books/v1/volumes/";
		if (volumeId == null || volumeId.isEmpty()) {
			throw new BookApiException("Invalid volume ID");
		}
		String uri = BasicUri + volumeId + "?key=" + API_KEY;
		final HttpGet getRequest = new HttpGet(uri);
		final CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpclient.execute(getRequest);
			final HttpEntity entity = response.getEntity();
			final ObjectMapper mapper = new ObjectMapper();

			final SpecificVolume result = mapper.readValue(entity.getContent(),SpecificVolume.class);
			response.close();
			return result;

		} catch (IOException e) {
			throw new BookApiException("Error requesting data from the Google Books API.", e);
		}
	}

	// Method SearchHistory
	public static class SearchHistory {
		private List<String> searchQueries = new ArrayList<>();

		public void addSearchQuery(String query) {
			searchQueries.add(query);
			if (searchQueries.size() > 5) {
				searchQueries.remove(0);
			}
		}

		public List<String> getSearchHistory() {
			return searchQueries;
		}
	}

	// method_3 Retrieving a list of a user's public bookshelves
	//// https://www.googleapis.com/books/v1/users/1112223334445556677/bookshelves&key=yourAPIKey

	public UserBookshelf getBookshelves(String userId) throws BookApiException {
		String BasicUri = "https://www.googleapis.com/books/v1/users/";
		String uri = BasicUri + userId + "/bookshelves" + "?key=" + API_KEY;

		final HttpGet getRequest = new HttpGet(uri);
		final CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpclient.execute(getRequest);
			final HttpEntity entity = response.getEntity();
			final ObjectMapper mapper = new ObjectMapper();

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				ErrorResponse errorResponse = mapper.readValue(entity.getContent(), ErrorResponse.class);
				if (errorResponse.getStatusMessage() != null)
					throw new BookApiException("Error occurred on API call: " + errorResponse.getStatusMessage());
			}

			final UserBookshelf BookshelvesResult = mapper.readValue(entity.getContent(), UserBookshelf.class);
			return BookshelvesResult;

		} catch (IOException e) {
			throw new BookApiException("Error requesting data from the Google Books API.", e);
		}
	}

	// METHOD_4 Retrieving a specific public bookshelf
	// https://www.googleapis.com/books/v1/users/1112223334445556677/bookshelves/3?key=yourAPIKey

	public SpecificPublicBookshelf getSpecificBookshelf(String userId, String bookshelfId)
			throws BookApiException, IOException {
		String BasicUri = "https://www.googleapis.com/books/v1/users/";

		int id = 0;
		if (bookshelfId.equals("1001")) {
			id = 1001;
		} else if (bookshelfId.equals("0")) {
			id = 0;
		} else {
			throw new IllegalArgumentException("bookshelfId must be either \"1001\" or \"0\"");
		}

		String uri = BasicUri + userId + "/bookshelves/" + id + "?key=" + API_KEY;

		final HttpGet getRequest = new HttpGet(uri);
		final CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpclient.execute(getRequest);
			final HttpEntity entity = response.getEntity();
			final ObjectMapper mapper = new ObjectMapper();

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				ErrorResponse errorResponse = mapper.readValue(entity.getContent(), ErrorResponse.class);
				if (errorResponse.getStatusMessage() != null)
					throw new BookApiException("Error occurred on API call: " + errorResponse.getStatusMessage());
			}

			final SpecificPublicBookshelf SpecificBookshelf = mapper.readValue(entity.getContent(),
					SpecificPublicBookshelf.class);
			return SpecificBookshelf;

		} catch (IOException e) {
			throw new BookApiException("Error requesting data from the Google Books API.", e);
		}
	}

	// METHOD_5
	public ListVolumesBookshelf getVolumesFromBookshelf(String userId, String bookshelfId) throws BookApiException {
		String BasicUri = "https://www.googleapis.com/books/v1/users/";
		int id = 0;
		if (bookshelfId.equals("1001")) {
			id = 1001;
		}
		String uri = BasicUri + userId + "/bookshelves/" + id + "/volumes" + "?key=" + API_KEY;

		final HttpGet getRequest = new HttpGet(uri);
		final CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpClient.execute(getRequest);
			final HttpEntity entity = response.getEntity();
			final ObjectMapper mapper = new ObjectMapper();

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				ErrorResponse errorResponse = mapper.readValue(entity.getContent(), ErrorResponse.class);
				if (errorResponse.getStatusMessage() != null)
					throw new BookApiException("Error occurred on API call: " + errorResponse.getStatusMessage());
			}

			final ListVolumesBookshelf volumeList = mapper.readValue(entity.getContent(), ListVolumesBookshelf.class);
			return volumeList;
		} catch (IOException e) {
			throw new BookApiException("Error requesting data from the Google Books API.", e);
		}

	}
}
