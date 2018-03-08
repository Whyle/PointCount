package xyz.whyle.pointcount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MyData
{
	// {
    //   "data":[{"name": "李雅洁"},
    //           {"name": "卓毅豪"}],
    //   "group":"3"
    // }

	@SerializedName("group")
	@Expose
	private String group;
	
	@SerializedName("data")
	@Expose
    private List<Data> data;

	public void setGroup(String group)
	{
		this.group = group;
	}

	public String getGroup()
	{
		return group;
	}

	public void setData(List<Data> data)
	{
		this.data = data;
	}

	public List<Data> getData()
	{
		return data;
	}

    
	
    public class Data {

		@SerializedName("id")
		@Expose
        private  String id;
		
		@SerializedName("name")
		@Expose
        private  String name;
		
		@SerializedName("sex")
		@Expose
        private String sex;
		
		@SerializedName("birthday")
		@Expose
        private String birthday;
		
		@SerializedName("phone")
		@Expose
        private String phone;
		
		@SerializedName("group")
		@Expose
        private String group;
		
		@SerializedName("address")
		@Expose
        private String address;
		
		public void setId(String id)
		{
			this.id = id;
		}

		public String getId()
		{
			return id;
		}
		
		public void setName(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return name;
		}

		public void setSex(String sex)
		{
			this.sex = sex;
		}

		public String getSex()
		{
			return sex;
		}

		public void setBirthday(String birthday)
		{
			this.birthday = birthday;
		}

		public String getBirthday()
		{
			return birthday;
		}

		public void setPhone(String phone)
		{
			this.phone = phone;
		}

		public String getPhone()
		{
			return phone;
		}

		public void setGroup(String group)
		{
			this.group = group;
		}

		public String getGroup()
		{
			return group;
		}

		public void setAddress(String address)
		{
			this.address = address;
		}

		public String getAddress()
		{
			return address;
		}
	}
}
