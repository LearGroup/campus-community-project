package hql;

public class StringSplitTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str="skey=s%3AlTnjXpB9ddHh_zZ0-BNOlXYo99eMnGoA.ED5XZ9Gaxh8ok9zir60EaYWtLBRPiEbv6FosuQfHjG4; Path=/; HttpOnly";
		String id = "sess:"+str.split("\\.")[0].substring(9);
		String target=id.substring(9);
		System.out.println(id);

	}

}
