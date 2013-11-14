package test;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.colman.rest.RequestManager;

public class RequestManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetQuery() {
		final List<BasicNameValuePair> qparams = new ArrayList<BasicNameValuePair>();
		qparams.add(new BasicNameValuePair("sq_location", "Brewer+Pool"));
		qparams.add(new BasicNameValuePair("sq_session_type", "Lane+Swim"));

		assertTrue(RequestManager.getQuery(qparams).equalsIgnoreCase(
				"sq_location=Brewer+Pool&sq_session_type=Lane+Swim&"));
	}

	@Test
	public void testGetHttpGet() {
		List<BasicNameValuePair> qparams = new ArrayList<BasicNameValuePair>();

		qparams.add(new BasicNameValuePair("sq_location", "Brewer+Pool"));
		qparams.add(new BasicNameValuePair("sq_session_type", "Lane+Swim"));

		final String query = RequestManager.getQuery(qparams);

		final HttpGet httpGet = RequestManager.getHttpGet("app06.ottawa.ca",
				-1, "/cgi-bin/schedulesearch/searchschedule.pl", query);

		String string = httpGet.getURI().toString();

		assertTrue(string
				.equalsIgnoreCase("http://app06.ottawa.ca/cgi-bin/schedulesearch/searchschedule.pl?sq_location=Brewer+Pool&sq_session_type=Lane+Swim&"));
	}

	@Test
	public void testGetResponse() {
		List<BasicNameValuePair> qparams = new ArrayList<BasicNameValuePair>();

		qparams.add(new BasicNameValuePair("stylesheet",
				"http://app06.ottawa.ca/templates/xslt/public_swimming_results_en.xsl"));
		qparams.add(new BasicNameValuePair("sq_event", "Swimming"));
		qparams.add(new BasicNameValuePair("sq_lang", "en"));
		qparams.add(new BasicNameValuePair("sort",
				"location+asc,start_date+asc,dayNo+asc,start_time+asc,session_type+asc"));
		qparams.add(new BasicNameValuePair("ret",
				"http://ottawa.ca/e/CON061600"));
		qparams.add(new BasicNameValuePair("sq_location", "Brewer+Pool"));
		qparams.add(new BasicNameValuePair("sq_session_type", "Lane+Swim"));
		qparams.add(new BasicNameValuePair("sq_keywords1", ""));
		
		final String query = RequestManager.getQuery(qparams);

		final HttpGet httpGet = RequestManager.getHttpGet("app06.ottawa.ca",
				-1, "/cgi-bin/schedulesearch/searchschedule.pl", query);


		 System.out.println(httpGet.getURI());
		
		final InputStream response = RequestManager.getResponse(httpGet);
		
		
	}
}
