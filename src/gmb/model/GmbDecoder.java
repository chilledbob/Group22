package gmb.model;

import java.io.UnsupportedEncodingException;
import org.salespointframework.core.user.UserIdentifier;

public class GmbDecoder {

		private static byte[] bytes = null;
		
		public static String decodeUTF8String(String encoded) throws UnsupportedEncodingException{
			bytes = encoded.getBytes();
			String s = new String(bytes,"UTF-8");
			System.out.println(s);
			return s;
		}
		
		public static UserIdentifier decodeUTF8String(UserIdentifier encoded) throws UnsupportedEncodingException{
			bytes = encoded.toString().getBytes();
			String uid = new String(bytes,"UTF-8");
			return new UserIdentifier(uid);
		}

}
