package xyz.whyle.pointcount.server;
import android.app.AlertDialog;
import android.content.Context;
import java.util.ArrayList;
import xyz.whyle.pointcount.MyData;
import android.app.AlertDialog.Builder;
import xyz.whyle.pointcount.MyData.Data;
import android.content.*;

public class PersonDetail
{

	private AlertDialog dialog;
	
	public PersonDetail(Context c, MyData.Data array)
	{
		
		dialog = new AlertDialog.Builder(c).create();
		MyData.Data user = array;
		dialog.setTitle(user.getName());
		dialog.setMessage(
		"Sex.....: " + user.getSex() + "\n" +
		"BirthDay: " + user.getBirthday() + "\n" +
		"Phone...: " + user.getPhone()  + "\n" +
		"Group...:" + user.getGroup()  + "\n" +
		"Address.:" + user.getAddress()
						  
						  
						  );
		dialog.setButton("Ok", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					dialog.dismiss();
				}
			});
		
	}
	
	public void show()
	{
			dialog.show();
	}
	
}
