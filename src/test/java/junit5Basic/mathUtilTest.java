package junit5Basic;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;

@TestMethodOrder(OrderAnnotation.class)
class mathUtilTest 
{
	static String APIbodyTOkenLOgin;
	static List<Header> headerlist = new ArrayList<Header>();
	
	public mathUtilTest()
	{
		
		
		RestAssured.baseURI  = "http://37.187.142.142/DigitalBankTesting";
	}

	//TOKEN STARTS

	@Nested
	@DisplayName("Token Api's")
	class Token 
	{
		//static String APIbodyTOkenLOgin;
		@Test
		//@Order(1) 
		public void postDigitalbank_API_TokenLogIn()
		{
			System.out.println("----------API_TokenLogIn----------");
			String APIBody = "{}";

		    List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			Headers headers = new Headers(headerlist);
			
			System.out.println(headers);
			
			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\Api_token_LOGin.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/api/Token/LogIn");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\Api_token_LOGin.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/api/Token/Login");
			}

			//r.prettyPrint();

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			APIbodyTOkenLOgin = body.asString(); 	///I COMMENTED THIS OUT FOR CHECKING response
			System.out.println("checking apiTokenfromLogin "+APIbodyTOkenLOgin); ///I COMMENTED THIS OUT FOR CHECKING response
			System.out.println();

			//if(bodyAsString.contains("Fetched Successful")==false)
			{
				//		System.out.println(" in iff");
				//	fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			//	assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
		}

		@Test 	//WORKING ON THIS
		//@Order(2) 
		public void postDigitalbank_API_ValidateOTP()
		{	//System.out.println("inside checking_APIbodyTOkenLOgin");
			
			for(int i=0; i<2; i++)
			{
				System.out.println("----------API_ValidateOTP----------");
				System.out.println("checking apiTokenfromLogin_inside_OTP** "+ APIbodyTOkenLOgin);
				String APIBody = "{}";
				String valueLoginTokenCHECK;
				
				List<Header> headerlist = new ArrayList<Header>();
				headerlist.add(new Header("Content-Type","application/json"));
				headerlist.add(new Header("device-id","1"));
				headerlist.add(new Header("user-agent","postman"));
				//headerlist.add(new Header("device-type","mobile"));
				//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
				headerlist.add(new Header("user-host-name","salman"));
				headerlist.add(new Header("user-language","English"));
				headerlist.add(new Header("user-host-address","::::0"));
				
				
				
				Headers headers = new Headers(headerlist);

				//System.out.println("THIS HERE" +headers);
			
				
				if(i==0) //this must pass
				{
					headerlist.add(new Header("Authorization","bearer "+APIbodyTOkenLOgin));
					headerlist.add(new Header("device-type","mobile"));
					headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
				}
				if(i==1)//this must fail.
				{
					headerlist.add(new Header("Authorization","bearerr "+APIbodyTOkenLOgin));
					headerlist.add(new Header("device-type","xyz"));
					headerlist.add(new Header("license-key",""));
				}
				
				

				valueLoginTokenCHECK="bearer "+APIbodyTOkenLOgin;
				//System.out.println("B_valueLoginTokenCHECK "+valueLoginTokenCHECK);

				

				String Authorization = headers.getValue("Authorization");
				//System.out.println("A_valueLoginTokenCHECK "+ Authorization);
				//System.out.println(APIbodyTOkenLOgin);

				Response r = given().body(APIBody).
						headers(headers).
						when().
						post("/api/Token/ValidateOTP");

				ResponseBody body = r.getBody();
				int statusCode = r.getStatusCode();

				assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

				String bodyAsString = body.asString();
				//System.out.println(bodyAsString);
				
				String LisenceNum = headers.getValue("license-key");
				String device_type = headers.getValue("device-type");
				
				
					System.out.println("i am in fail test");
					//System.out.println("LisenceNum :"+LisenceNum);
					//System.out.println("device_type :"+device_type);
					//assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/, "Response body shouldn't contain Fetched Successful");
					if(!(valueLoginTokenCHECK.equals(Authorization)) || LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
					{
						if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
						{
							assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/, "Response body shouldn't contain Fetched Successful");
							System.out.println("Lisence-Key invalid");
						}
						if(device_type!="mobile")
						{
							//assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/, "Response body shouldn't contain Fetched Successful");
							System.out.println("device_type invalid");
						}
						if(!(valueLoginTokenCHECK.equals(Authorization)))
						{
							//assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/, "Response body shouldn't contain Fetched Successful");
							System.out.println("Authorization invalid");
						}
						fail();
					}		
				
			}
		}
	}

	//BENEFICIARY STARTS
	@Nested
	@DisplayName("Beneficiary Api's")
	class beneficiary
	{
		@Test
		//@Order(5) 
		public void postDigitalbank_customer_benef_GETid_269_THIS_SHOULD_PASS()
		{
			System.out.println("----------customer_benef_GETid_269_Positive_Case----------");

			//String APIBody = "{}";// //e.g.- "{\"key1\":\"value1\",\"key2\":\"value2\"}"
			//	String APIBody = "{\"ReportingDate\":\"31-Mar-2018\"}";
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","")); //check this
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//System.out.println(headers);


			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Beneficiary/Get/269");

			//r.prettyPrint();
			//String body = r.getBody().asString();
			ResponseBody  body = r.getBody();
			//System.out.println(body);

			int statusCode = r.getStatusCode();

			// Assert that correct status code is returned.
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);


			String bodyAsString = body.asString();
			System.out.println(bodyAsString);
			
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
						
		}
		
		
		@Test ///testing changes in here
		@Order(7) 
		public void postDigitalbank_customer_benef_GETid_269_THIS_MUST_FAIL()
		{
			System.out.println("----------customer_benef_GETid_269_Negative_Case----------");
			
			
			for(int i=0; i<2; i++)
			{
				String APIBody = "{}";

				List<Header> headerlist = new ArrayList<Header>();
				headerlist.add(new Header("Content-Type","application/json"));
				headerlist.add(new Header("device-id","1"));
				headerlist.add(new Header("user-agent","postman"));
				//headerlist.add(new Header("device-type","mobile")); //check this
				//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
				headerlist.add(new Header("user-host-name","salman"));
				headerlist.add(new Header("user-language","English"));
				headerlist.add(new Header("user-host-address","::::0"));

				if(i==0) //Keep this actual key and values
				{
					//System.out.println("running test for true ");
					headerlist.add(new Header("device-type","mobile")); //check this
					headerlist.add(new Header("license-key","")); //check this
				}
				if(i==1) //Keep this non-actual key and values
				{
					//System.out.println("running test for false ");
					headerlist.add(new Header("device-type","")); //check this
					headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
				}

				Headers headers = new Headers(headerlist);

				//System.out.println(headers);
				//int i=0;
				//for (i = 0; i < headerlist.size(); i++) 
				//{
				//		System.out.println("thisss "+ headerlist.get(3));
				//	}
				//	System.out.println("this is size "+ i);
				//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

				//headerlist.add(new Header("device-type","changed"));

				//System.out.println("thisss is changed "+ headerlist.get(3));


				Response r = given().body(APIBody).
						headers(headers).
						when().
						post("/customer/Beneficiary/Get/269");

				ResponseBody  body = r.getBody();

				int statusCode = r.getStatusCode();

				assertEquals(statusCode /*actual value*/, 200 /*expected value*/);	
				String bodyAsString = body.asString();

				String LisenceNum = headers.getValue("license-key");
				String device_type = headers.getValue("device-type");	
						
		
				
				
					
					//System.out.println("LisenceNum :"+LisenceNum);
					//System.out.println("device_type :"+device_type);
					//assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/, "Response body shouldn't contain Fetched Successful");
					if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
					{
						if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
						{
							if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
							{
							System.out.println("Lisence-Key invalid checked (API responded wrong)");
							}
							else
							{
								continue;
							}
							
						}
						if(device_type!="mobile")
						{
							if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
							{
							System.out.println("Device type invalid checked (API responded wrong)");
							}
							else
							{
								continue;
							}
						}
						
						
						
						fail();
					}		
				
			}
		}

		@Test
		@Order(3) 
		public void postDigitalbank_customer_benef()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Beneficiary");

			//r.prettyPrint();
			//String body = r.getBody().asString();
			ResponseBody  body = r.getBody();
			//System.out.println(body);

			int statusCode = r.getStatusCode();

			// Assert that correct status code is returned.
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
			
		}
		
		@Test
		@Order(4) 
		public void postDigitalbank_customer_benef_Negative()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY Negative Case----------");
			String APIBody = "{}";

			for(int i=0;i<2;i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}


			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Beneficiary");

			//r.prettyPrint();
			//String body = r.getBody().asString();
			ResponseBody  body = r.getBody();
			//System.out.println(body);

			int statusCode = r.getStatusCode();

			// Assert that correct status code is returned.
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}

		@Test
		@Order(5) 
		public void postDigitalbank_customer_benef_GetBankName_IBAN_BH40BBKU00200002064187()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY_GetBankName_IBAN_BH40BBKU00200002064187----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Beneficiary/GetBankName/BH40BBKU00200002064187");

			//r.prettyPrint();
			//String body = r.getBody().asString();
			ResponseBody  body = r.getBody();
			//System.out.println(body);

			int statusCode = r.getStatusCode();

			// Assert that correct status code is returned.
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
			
		}
		
		@Test
		@Order(6) 
		public void postDigitalbank_customer_benef_GetBankName_IBAN_BH40BBKU00200002064187_Negative()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY_GetBankName_IBAN_BH40BBKU00200002064187 Negative Case----------");
			String APIBody = "{}";

			for(int i=0;i<2;i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}


			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Beneficiary/GetBankName/BH40BBKU00200002064187");

			//r.prettyPrint();
			//String body = r.getBody().asString();
			ResponseBody  body = r.getBody();
			//System.out.println(body);

			int statusCode = r.getStatusCode();

			// Assert that correct status code is returned.
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			

			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
			
		}

		@Test
		@Order(7) 
		public void postDigitalbank_customer_benef_SAVE() throws IOException
		{
			System.out.println("----------CUSTOMER_BENEFICIARY_SAVE----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","Talha"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY_save.json");
			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY_save.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/Beneficiary/save");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\body_CUST_BENEFICIARY_save.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/Beneficiary/save");
			}

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Successfully Updated") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
			
		}
		@Test
		@Order(8) 
		public void postDigitalbank_customer_benef_SAVE_Negative() throws IOException
		{
			System.out.println("----------CUSTOMER_BENEFICIARY_SAVE_Negative----------");
			String APIBody = "{}";
			
			for(int i=0;i<2;i++)
			{

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY_save.json");
			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY_save.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/Beneficiary/save");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\body_CUST_BENEFICIARY_save.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/Beneficiary/save");
			}

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}

		@Test
		public void postDigitalbank_Customer_benef_BenefTYPE()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY_BenefTYPE----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY_save.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Beneficiary/BeneficiaryType");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
			
			}

		
		@Test
		public void postDigitalbank_Customer_benef_BenefTYPE_Negative()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY_BenefTYPE Negative Case----------");
			String APIBody = "{}";

			for(int i=0;i<2;i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			
			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY_save.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Beneficiary/BeneficiaryType");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
			}
		
		@Test
		public void postDigitalbank_Customer_benef_BenefDropDown()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY_BenefDropDown----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY_save.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Beneficiary/BeneficiaryDropdown");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
			
		}
		
		@Test
		public void postDigitalbank_Customer_benef_BenefDropDown_Negative()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY_BenefDropDown Negative Case----------");
			String APIBody = "{}";

			for(int i=0;i<2;i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}


			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY_save.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Beneficiary/BeneficiaryDropdown");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}

		@Test
		public void postDigitalbank_Customer_benef_Authorise()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY__Authorise----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_beneficiary_authorise.json");

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_beneficiary_authorise.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/Beneficiary/Authorise");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\customer_beneficiary_authorise.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/Beneficiary/Authorise");
			}

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			if(!bodyAsString.contains("Successfully Updated") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
			
		}
		
		@Test
		public void postDigitalbank_Customer_benef_Authorise_Negative()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY__Authorise_Negative----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			
			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_beneficiary_authorise.json");

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_beneficiary_authorise.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/Beneficiary/Authorise");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\customer_beneficiary_authorise.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/Beneficiary/Authorise");
			}

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}	

		@Test
		public void postDigitalbank_Customer_benef_CANCEL()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY__CANCEL----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_beneficiary_authorise.json");

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\Customer_benef_cancel.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/Beneficiary/Cancel");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\Customer_benef_cancel.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/Beneficiary/Cancel");
			}

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Successfully Updated") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}	
		
		
		@Test
		public void postDigitalbank_Customer_benef_CANCEL_Negative()
		{
			System.out.println("----------CUSTOMER_BENEFICIARY__CANCEL Negative Case----------");
			String APIBody = "{}";
			
			for(int i=0; i<2;i++)
			{

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_beneficiary_authorise.json");

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\Customer_benef_cancel.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/Beneficiary/Cancel");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\Customer_benef_cancel.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/Beneficiary/Cancel");
			}

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}	

		@Test
		public void postDigitalbank_admin_benef()
		{
			System.out.println("----------ADMIN_BENEFICIARY----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Beneficiary");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}

		@Test
		public void postDigitalbank_admin_benef_Negative()
		{
			System.out.println("----------ADMIN_BENEFICIARY Negative Case----------");
			String APIBody = "{}";

			for(int i=0;i<2;i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Beneficiary");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}
		
		@Test
		public void postDigitalbank_Admin_benef_GETid_269()
		{
			System.out.println("----------Admin_benef_GETid_269----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Beneficiary/Get/269");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Admin_benef_GETid_269_Negative()
		{
			System.out.println("----------Admin_benef_GETid_269_Negative----------");
			String APIBody = "{}";

			for(int i=0;i<2;i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}


			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Beneficiary/Get/269");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
		}
		}

	}


	//COMMON STARTS
	@Nested
	@DisplayName("Common Api's")
	class Common
	{
		@Test
		public void postDigitalbank_Common_CountryDropDown()
		{
			System.out.println("----------Common_CountryDropDown----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/common/Common/CountryDropdown");

			//r.prettyPrint();
			//String body = r.getBody().asString();
			ResponseBody  body = r.getBody();
			//System.out.println(body);

			int statusCode = r.getStatusCode();

			// Assert that correct status code is returned.
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Common_CountryDropDown_Negative()
		{
			System.out.println("----------Common_CountryDropDown_Negative----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}


			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/common/Common/CountryDropdown");

			//r.prettyPrint();
			//String body = r.getBody().asString();
			ResponseBody  body = r.getBody();
			//System.out.println(body);

			int statusCode = r.getStatusCode();

			// Assert that correct status code is returned.
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}

		
		
		@Test
		public void postDigitalbank_Common_CurrencyDropDown()
		{
			System.out.println("----------Common_CurrencyDropDown----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/common/Common/CurrencyDropdown");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Common_CurrencyDropDown_Negative()
		{
			System.out.println("----------Common_CurrencyDropDown Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/common/Common/CurrencyDropdown");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}


	}

	//INVESTMENT STARTS
	@Nested
	@DisplayName("Investment Api's")
	class Investment
	{
		@Test
		public void postDigitalbank_Customer_invest()
		{
			System.out.println("----------customer_INVESTMENT----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Investment");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Customer_invest_Negative()
		{
			System.out.println("----------customer_INVESTMENT Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			

			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Investment");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}

		@Test
		public void postDigitalbank_Customer_Invest_GETid_2494()
		{
			System.out.println("----------Customer_Invest_GETid_2494----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Investment/Get/2494");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}

		@Test
		public void postDigitalbank_Customer_Invest_GETid_2494_Negative()
		{
			System.out.println("----------Customer_Invest_GETid_2494 Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Investment/Get/2494");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}

		
		@Test
		public void postDigitalbank_Customer_Invest_SAVE()
		{
			System.out.println("----------Customer_Invest_SAVE----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\Customer_Invest_SAVE_body.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/Investment/Save");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\Customer_Invest_SAVE_body.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/Investment/Save");
			}


			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Successfully Updated") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Customer_Invest_SAVE_Negative()
		{
			System.out.println("----------Customer_Invest_SAVE Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\Customer_Invest_SAVE_body.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/Investment/Save");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\Customer_Invest_SAVE_body.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/Investment/Save");
			}


			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}

		@Test
		public void postDigitalbank_Admin_invest()
		{
			System.out.println("----------ADMIN_investMENT----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Investment");
			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Admin_invest_Negative()
		{
			System.out.println("----------ADMIN_investMENT Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Investment");
			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
			
		}

		@Test
		public void postDigitalbank_Admin_Invest_GETid_2494()
		{
			System.out.println("----------Admin_Invest_GETid_2494----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Investment/Get/2494");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		
		@Test
		public void postDigitalbank_Admin_Invest_GETid_2494_Negative()
		{
			System.out.println("----------Admin_Invest_GETid_2494 Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++ )
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Investment/Get/2494");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
		}
		}
	}
	

	//NOTIFICATION STARTS
	@Nested
	@DisplayName("Notification Api's")
	class Notification
	{
		@Test
		public void postDigitalbank_Common_Notification()
		{
			System.out.println("----------Common_Notification----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/common/Notification");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		
		@Test
		public void postDigitalbank_Common_Notification_Negative()
		{
			System.out.println("----------Common_Notification Negative case----------");
			String APIBody = "{}";
			
			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			
			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/common/Notification");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}

		@Test
		public void postDigitalbank_Common_Notification_Review_11()
		{
			System.out.println("----------Common_Notification_Review_11----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/common/Notification/Review/11");
			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Successfully Updated") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		@Test
		public void postDigitalbank_Common_Notification_Review_11_Negative()
		{
			System.out.println("----------Common_Notification_Review_11 Negative Case----------");
			String APIBody = "{}";

			for(int i=0;i<3; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			
			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/common/Notification/Review/11");
			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
		
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
		}
		}
	}

	//ROLE STARTS
	@Nested
	@DisplayName("Role Api's")
	class Role
	{
		@Test
		public void postDigitalbank_Admin_Role()
		{
			System.out.println("----------Admin_Role----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Role");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			System.out.println(bodyAsString);
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}

		@Test
		public void postDigitalbank_Admin_Role_Negative()
		{
			System.out.println("----------Admin_Role Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Role");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
		
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			
			}
		}
		
		@Test
		public void postDigitalbank_Admin_Role_GetID_10()
		{
			System.out.println("----------Admin_Role_GetID_10----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Role/Get/10");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}

		@Test
		public void postDigitalbank_Admin_Role_GetID_10_Negative()
		{
			System.out.println("----------Admin_Role_GetID_10 Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Role/Get/10");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}
		
		@Test
		public void postDigitalbank_Admin_Role_SAVE()
		{
			System.out.println("----------Admin_Role_SAVE----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\admin_role_SAVE.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/admin/Role/Save");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\admin_role_SAVE.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/admin/Role/Save");
			}


			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Successfully Updated") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Admin_Role_SAVE_Negative()
		{
			System.out.println("----------Admin_Role_SAVE Negative----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			

			Headers headers = new Headers(headerlist);

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\admin_role_SAVE.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/admin/Role/Save");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\admin_role_SAVE.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/admin/Role/Save");
			}


			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
		}
		}
		
	}

	//SERVICE_REQUEST STARTS
	@Nested
	@DisplayName("ServiceRequest Api's")
	class ServiceRequest
	{
		@Test
		public void postDigitalbank_Customer_ServiceREQ()
		{
			System.out.println("----------Customer_ServiceRequest----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/ServiceRequest");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}

		@Test
		public void postDigitalbank_Customer_ServiceREQ_Negative()
		{
			System.out.println("----------Customer_ServiceRequest Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2;i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/ServiceRequest");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			
		}
		}

		
		@Test
		public void postDigitalbank_Customer_ServiceRequest_GETid_335()
		{
			System.out.println("----------Customer_Invest_GETid_2494----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/ServiceRequest/Get/335");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		
		@Test
		public void postDigitalbank_Customer_ServiceRequest_GETid_335_Negative()
		{
			System.out.println("----------Customer_Invest_GETid_2494 Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/ServiceRequest/Get/335");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			
		}
		}

		@Test
		public void postDigitalbank_Customer_ServiceRequest_SAVE()
		{
			System.out.println("----------Customer_Invest_SAVE----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_ServiceRequest_Save_body.json");
			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_ServiceRequest_Save_body.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/ServiceRequest/Save");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\customer_ServiceRequest_Save_body.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/ServiceRequest/Save");
			}

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Successfully Updated") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}


		@Test
		public void postDigitalbank_Customer_ServiceRequest_SAVE_Negative()
		{
			System.out.println("----------Customer_Invest_SAVE Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_ServiceRequest_Save_body.json");
			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_ServiceRequest_Save_body.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/ServiceRequest/Save");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\customer_ServiceRequest_Save_body.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/ServiceRequest/Save");
			}

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}

		
		@Test
		public void postDigitalbank_Customer_ServiceRequest_ServiceTypeDropdown()
		{
			System.out.println("----------Customer_ServiceRequest_ServiceTypeDropdown----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/ServiceRequest/ServiceTypeDropdown");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Customer_ServiceRequest_ServiceTypeDropdown_Negative()
		{
			System.out.println("----------Customer_ServiceRequest_ServiceTypeDropdown Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			
			
			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/ServiceRequest/ServiceTypeDropdown");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}
		

		@Test
		public void postDigitalbank_Customer_ServiceRequest_AuditYearDropdown()
		{
			System.out.println("----------Customer_ServiceRequest_AuditYearDropdown----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/ServiceRequest/AuditYearDropdown");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Customer_ServiceRequest_AuditYearDropdown_Negative()
		{
			System.out.println("----------Customer_ServiceRequest_AuditYearDropdown Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2 ;i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/ServiceRequest/AuditYearDropdown");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			
			}
		}
		

		@Test
		public void postDigitalbank_Customer_ServiceRequest_DurationDropdown()
		{
			System.out.println("----------Customer_ServiceRequest_DurationDropdown----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/ServiceRequest/DurationDropdown");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}

		
		@Test
		public void postDigitalbank_Customer_ServiceRequest_DurationDropdown_Negative()
		{
			System.out.println("----------Customer_ServiceRequest_DurationDropdown Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/ServiceRequest/DurationDropdown");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}
		
		@Test
		public void postDigitalbank_Admin_ServiceREQ()
		{
			System.out.println("----------ADMIN_ServiceREQ----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/ServiceRequest");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
			//assertNotNull(r);
		}
		
		@Test
		public void postDigitalbank_Admin_ServiceREQ_Negative()
		{
			System.out.println("----------ADMIN_ServiceREQ Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			
			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/ServiceRequest");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			//assertNotNull(r);
			}
		}

		@Test
		public void postDigitalbank_Admin_ServiceRequest_GETid_335()
		{
			System.out.println("----------Admin_ServiceRequest_GETid_335----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/ServiceRequest/Get/335");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		
		@Test
		public void postDigitalbank_Admin_ServiceRequest_GETid_335_Negative()
		{
			System.out.println("----------Admin_ServiceRequest_GETid_335 Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2;i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/ServiceRequest/Get/335");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			//assertNotNull(r);
			}
		}
		}
		
	}

	//STANDING_INSTRUCTION STARTS
	@Nested
	@DisplayName("StandingInstruction Api's")
	class StandingInstruction
	{
		@Test
		public void postDigitalbank_Customer_StandingInstruction()
		{
			System.out.println("----------Customer_StandingInstruction----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/StandingInstruction");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			if(bodyAsString.contains("Fetched Successful")==false)
			{
				System.out.println(" in iff");
				fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
		}
		
		
		@Test
		public void postDigitalbank_Customer_StandingInstruction_Negative()
		{
			System.out.println("----------Customer_StandingInstruction Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/StandingInstruction");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}


		@Test
		public void postDigitalbank_Customer_StandingInstruction_GetID_6()
		{
			System.out.println("----------Customer_StandingInstruction_GetID_6----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/StandingInstruction/Get/6");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Customer_StandingInstruction_GetID_6_Negative()
		{
			System.out.println("----------Customer_StandingInstruction_GetID_6 Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/StandingInstruction/Get/6");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}

		@Test
		public void postDigitalbank_Customer_StandingInstruction_SAVE()
		{
			System.out.println("----------Customer_StandingInstruction_SAVE----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/StandingInstruction/Save");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			if(!bodyAsString.contains("Fetched Successful") || !bodyAsString.contains("true"))
			{
			System.out.println("(API responded wrong)");
			fail();
			}
		}
		
		@Test
		public void postDigitalbank_Customer_StandingInstruction_SAVE_Negative()
		{
			System.out.println("----------Customer_StandingInstruction_SAVE Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}


			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/StandingInstruction/Save");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}


		@Test
		@Order(20)
		public void postDigitalbank_Customer_StandingInstruction_Authorise()
		{
			System.out.println("----------CUSTOMER_StandingInstruction__Authorise----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_beneficiary_authorise.json");

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\Customer_StandinInstruction_Authorized -.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/StandingInstruction/Authorise");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\Customer_StandinInstruction_Authorized -.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/StandingInstruction/Authorise");
			}

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			assertEquals(bodyAsString.contains("Record Successfully Updated") /*Expected value*/, true /*Actual Value*/, "Response body contains Record Successfully Updated");
		}	
		
		
		@Test
		@Order(21)
		public void postDigitalbank_Customer_StandingInstruction_Authorise_Negative()
		{
			System.out.println("----------CUSTOMER_StandingInstruction__Authorise Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}
			

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\customer_beneficiary_authorise.json");

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\Customer_StandinInstruction_Authorized -.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/StandingInstruction/Authorise");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\Customer_StandinInstruction_Authorized -.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/StandingInstruction/Authorise");
			}

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
		
			}
			}	
		
		

		@Test
		public void postDigitalbank_Customer_StandingInstruction_Dropdown_Negative()
		{
			System.out.println("----------CUSTOMER_StandingInstruction__Dropdown Negative----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/StandingInstruction/InstructionDropdown");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}

			}
		}

		@Test
		public void postDigitalbank_Customer_StandingInstruction_Dropdown()
		{
			System.out.println("----------CUSTOMER_StandingInstruction__Dropdown----------");
			String APIBody = "{}";

			
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/StandingInstruction/InstructionDropdown");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			if(bodyAsString.contains("Fetched Successful")==false)
			{
				System.out.println(" in iff");
				fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
			
			}


		@Test
		public void postDigitalbank_Admin_StandingInstruction()
		{
			System.out.println("----------Admin_StandingInstruction----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/StandingInstruction");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			if(bodyAsString.contains("Fetched Successful")==false)
			{
				System.out.println(" in iff");
				fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
		}
		
		@Test
		public void postDigitalbank_Admin_StandingInstruction_Negative()
		{
			System.out.println("----------Admin_StandingInstruction Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/StandingInstruction");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}

		
			}
			}


		@Test
		public void postDigitalbank_Admin_StandingInstruction_GetID_6()
		{
			System.out.println("----------Admin_StandingInstruction_GetID_6----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/StandingInstruction/Get/6");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/, "Response body contains Fetched Successful");
		}
		
		@Test
		public void postDigitalbank_Admin_StandingInstruction_GetID_6_Negative()
		{
			System.out.println("----------Admin_StandingInstruction_GetID_6 Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			//File file = new File("D:\\SitProsWork\\eclipse stuff\\body_CUST_BENEFICIARY.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/StandingInstruction/Get/6");

			ResponseBody  body = r.getBody();

			int statusCode = r.getStatusCode();
			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();
			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
		
			}
			}

	}


	//TRANSACTION STARTS
	@Nested
	@DisplayName("Transaction Api's")
	class Transaction
	{
		@Test
		public void postDigitalbank_Customer_Transactions()
		{
			System.out.println("----------Customer_Transactions----------");	
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Transactions");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			if(bodyAsString.contains("Fetched Successful")==false)
			{
				System.out.println(" in iff");
				fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
		}
		
		@Test
		public void postDigitalbank_Customer_Transactions_Negative()
		{
			System.out.println("----------Customer_Transactions Negative Cases----------");	
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Transactions");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}


		@Test
		public void postDigitalbank_Customer_Transactions_GetID_2475()
		{
			System.out.println("----------Customer_Transactions_GetID_2475----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Transactions/Get/2475");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			if(bodyAsString.contains("Fetched Successful")==false)
			{
				System.out.println(" in iff");
				fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
		}
		
		
		@Test
		public void postDigitalbank_Customer_Transactions_GetID_2475_Negative()
		{
			System.out.println("----------Customer_Transactions_GetID_2475 Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Transactions/Get/2475");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}
		
		

		@Test
		public void postDigitalbank_Customer_Transactions_SAVE()
		{
			System.out.println("----------Customer_Transactions_SAVE----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\Customer_Transaction_SAVE.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/Transactions/Save");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\Customer_Transaction_SAVE.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/Transactions/Save");
			}

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();


			assertEquals(bodyAsString.contains("Record Successfully Updated") /*Expected value*/, true /*Actual Value*/);
		}



		@Test
		public void postDigitalbank_Customer_Transactions_SAVE_Negative()
		{
			System.out.println("----------Customer_Transactions_SAVE Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			Response r;
			File file = new File("D:\\SitProsWork\\eclipse stuff\\Customer_Transaction_SAVE.json");
			if (file.exists())
			{
				r = given().body(file).
						headers(headers).
						when().
						post("/customer/Transactions/Save");
			}
			else
			{
				File filee = new File("D:\\Implementation\\Jenkins_Workspace\\Testing_Degital_Bank\\Junit-Framework\\Customer_Transaction_SAVE.json");
				r = given().body(filee).
						headers(headers).
						when().
						post("/customer/Transactions/Save");
			}

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}

		
		//added till here.

		@Test
		public void postDigitalbank_Customer_Transactions_AccountDropdown()
		{
			System.out.println("----------Customer_Transactions_GetID_AccountDropdown----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Transactions/AccountDropdown");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			if(bodyAsString.contains("Fetched Successful")==false)
			{
				System.out.println(" in iff");
				fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
		}
		
		@Test
		public void postDigitalbank_Customer_Transactions_AccountDropdown_Negative()
		{
			System.out.println("----------Customer_Transactions_GetID_AccountDropdown Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/customer/Transactions/AccountDropdown");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}


		@Test
		public void postDigitalbank_Admin_Transactions()
		{
			System.out.println("----------Admin_Transactions----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Transactions");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			if(bodyAsString.contains("Fetched Successful")==false)
			{
				System.out.println(" in iff");
				fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
		}
		
		@Test
		public void postDigitalbank_Admin_Transactions_Negative()
		{
			System.out.println("----------Admin_Transactions Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Transactions");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}


		@Test
		public void postDigitalbank_Admin_Transactions_GetID_2475()
		{
			System.out.println("----------Admin_Transactions_GetID_2475----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Transactions/Get/2475");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			if(bodyAsString.contains("Fetched Successful")==false)
			{
				System.out.println(" in iff");
				fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
		}
		
		@Test
		public void postDigitalbank_Admin_Transactions_GetID_2475_Negative()
		{
			System.out.println("----------Admin_Transactions_GetID_2475 Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/Transactions/Get/2475");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();


			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
		}
		}
		
	}


	//USER STARTS
	@Nested
	@DisplayName("User Api's")
	class User
	{
		@Test
		public void postDigitalbank_Admin_User()
		{
			System.out.println("----------Admin_User----------");	
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/User");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			if(bodyAsString.contains("Fetched Successful")==false)
			{
				System.out.println(" in iff");
				fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
		}
		
		@Test
		public void postDigitalbank_Admin_User_Negative()
		{
			System.out.println("----------Admin_User Negative Case----------");	
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/User");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);

			String bodyAsString = body.asString();

			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
			}
		}


		@Test
		public void postDigitalbank_Admin_User_GetID_807()
		{
			System.out.println("----------Admin_User_GetID_807----------");
			String APIBody = "{}";

			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			headerlist.add(new Header("device-type","mobile"));
			headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/User/Get/807");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);
			String bodyAsString = body.asString();

			if(bodyAsString.contains("Fetched Successful")==false)
			{
				System.out.println(" in iff");
				fail("Should not have thrown invalidity");
			}
			//fail("Should not have thrown any exception");
			assertEquals(bodyAsString.contains("Fetched Successful") /*Expected value*/, true /*Actual Value*/);
		}
		
		
		@Test
		public void postDigitalbank_Admin_User_GetID_807_Negative()
		{
			System.out.println("----------Admin_User_GetID_807 Negative Case----------");
			String APIBody = "{}";

			for(int i=0; i<2; i++)
			{
			List<Header> headerlist = new ArrayList<Header>();
			headerlist.add(new Header("Content-Type","application/json"));
			headerlist.add(new Header("device-id","1"));
			headerlist.add(new Header("user-agent","postman"));
			//headerlist.add(new Header("device-type","mobile"));
			//headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8"));
			headerlist.add(new Header("user-host-name","salman"));
			headerlist.add(new Header("user-language","English"));
			headerlist.add(new Header("user-host-address","::::0"));
			
			if(i==0) //Keep this actual key and values
			{
				//System.out.println("running test for true ");
				headerlist.add(new Header("device-type","mobile")); //check this
				headerlist.add(new Header("license-key","")); //check this
			}
			if(i==1) //Keep this non-actual key and values
			{
				//System.out.println("running test for false ");
				headerlist.add(new Header("device-type","")); //check this
				headerlist.add(new Header("license-key","EF834317-1486-48E6-91EC-04D76FF720B8")); //check this
			}

			Headers headers = new Headers(headerlist);

			File file = new File("D:\\SitProsWork\\eclipse stuff\\body.json");

			Response r = given().body(APIBody).
					headers(headers).
					when().
					post("/admin/User/Get/807");

			ResponseBody  body = r.getBody();
			int statusCode = r.getStatusCode();

			assertEquals(statusCode /*actual value*/, 200 /*expected value*/);
			String bodyAsString = body.asString();

			String LisenceNum = headers.getValue("license-key");
			String device_type = headers.getValue("device-type");
			
			if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8" || device_type!="mobile")
			{
				if(LisenceNum!="EF834317-1486-48E6-91EC-04D76FF720B8")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Lisence-Key invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
					
				}
				if(device_type!="mobile")
				{
					if(bodyAsString.contains("Fetched Successful") || bodyAsString.contains("true"))
					{
					System.out.println("Device type invalid checked (API responded wrong)");
					}
					else
					{
						continue;
					}
				}
				
				
				
				fail();
			}
		}
		}
	}	

