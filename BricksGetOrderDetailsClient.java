public class BricksGetOrderDetailsClient{
	
	public static void main (String[] args){
	JSONObject json=new JSONObject();
		try(
		Class.forName("org.postgresql.Driver");
		Connection con=DriverManager.getConnection("jdbc:postgresql://{DB IP}:{DB PORT}/{DB NAME}","{USERNAME}","{PASSWORD}");
		){
		json.put("customerUniqId","BRICK191");
		
		Client client = Client.create();	
		WebResource webResource = client .resource("{hostedURL}/business/GetBrickData/);
		ClientResponse resp = webResource.accept("application/json").entity(  json.toString() ).post(ClientResponse.class);
 		String output = resp.getEntity(String.class);
	      	  System.out.println("Response : "+output);
		}catch(exception e){
		e.printStackTrace();	
		}	
	}
}