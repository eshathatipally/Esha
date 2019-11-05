@path("/business")
@produces("application/json")
public class BricksOrderingREST (){
// ordering bvricks and getting a order Id
@path ("orderBricks")
@POST
@produces("application/json"){
	public string insertOrders(@PathParam("customerName") String customerName,@PathParam("BrickCount") int BrickCount){
               string orderId=null;
 JSONObject res=new JSONObject();
	PerparedStatement ps=null;
	String sql=null;
	resultSet rs=null;
	int max=0;
	String customerUniqId=null;		
		try(
Class.forName("org.postgresql.Driver");
Connection con=DriverManager.getConnection("jdbc:postgresql://{DB IP}:{DB PORT}/{DB NAME}","{USERNAME}","{PASSWORD}");
){
sql="select nextval("orders") "; //orders is a sequence
ps=con.prepareStatement(sql);
rs=ps.executeQuery();
if(rs!= null && rs.next(){
	max=rs.getInt(1);
}

customerUniqId="BRICK19"+String.value(max);
	sql="insert into bricks_orders(id,customer_name,customer_uniq_id,no_bricks,order_date) values (?,?,?,?,now())";
	ps=con.prepareStatement(sql);
	ps.setInt(1,max);
	ps.SetString(2,customerName);
	ps.setString(3,customerUniqId);
	ps.setInt(4,BrickCount);
	ps.executeUpdate();

res.put("msg","Your Order Has been recieved and your order ref id is "+customerUniqId);
		}catch(exception e){
		e.printStackTrace();	
		}

	return res;
	}


}

@path ("GetBrickData")
@GET
@produces("application/json"){
	public string getOrderData(@PathParam("customerUniqId") String customerUniqId){
             JSONObject res=new JSONObject();
	PerparedStatement ps=null;
	String sql=null;
	resultSet rs=null;
		try(
Class.forName("org.postgresql.Driver");
Connection con=DriverManager.getConnection("jdbc:postgresql://{DB IP}:{DB PORT}/{DB NAME}","{USERNAME}","{PASSWORD}");
){
sql="select customer_name,customer_uniq_id,no_bricks,coalesce(dispatch_status,false) as dispatch_status from  bricks_orders where  customer_uniq_id='"+customerUniqId+"' ";
ps=con.prepareStatement(sql);
rs=ps.executeQuery();
if(rs!= null && rs.next(){
res.put("msg","Hi "+"+rs.getString("customer_name")+"+" ,Your Order is for "+rs.getInt("no_bricks")+" Bricks is recieved.") 	
}else{
res.put("msg","Invalid Order Id,Please Enter Correct Order id ");
}

		}catch(exception e){
		e.printStackTrace();	
		}

	return res;
	}

// updating order status 
@path ("updateOrder")
@POST
@produces("application/json"){
	public JSONObject updateOrders(@PathParam("customerUniqId") String customerUniqId,@QueryParam("BrickCount") int BrickCount){
               string orderId=null;
	JSONObject res=new JSONObject();
	PerparedStatement ps=null;
	String sql=null;
	resultSet rs=null;
	int dispatchStatus=0; 	

		try(
Class.forName("org.postgresql.Driver");
Connection con=DriverManager.getConnection("jdbc:postgresql://{DB IP}:{DB PORT}/{DB NAME}","{USERNAME}","{PASSWORD}");
){

sql="select case when coalesce(dispatch_status,false)=true then 1 else 0  end   as dispatch_status from  bricks_orders where  customer_uniq_id='"+customerUniqId+"' ";
ps=con.prepareStatement(sql);
rs=ps.executeQuery();
if(rs!= null && rs.next(){
 dispatchStatus=rs.getInt(1);	
}else{
res.put("msg","Invalid Order Id,Please Enter Correct Order id");
}
if(dispatchStatus==1{
res.put("msg","Your Order has been dispatched.You cannot update it ");
}else{
	sql="update  bricks_orders set no_bricks=? where customer_uniq_id=? and coalesce(dispatch_status,false)=false;";
	ps=con.prepareStatement(sql);
	ps.setInt(1,BrickCount);
	ps.setString(2,customerUniqId);
	int sqlRes =ps.executeUpdate();
if(sqlRes >0{
res.put("msg","Your Order Has been updated for "+BrickCount+" bricks and your order ref id is "+customerUniqId+"");
}
		}catch(exception e){
		e.printStackTrace();	
		}

	return res;
	}


}
}