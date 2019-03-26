package com.yonyou.business.entity;

import java.io.Serializable;
import java.util.Date;

public class TokenEntity implements Serializable ,Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7339330680881786948L;
	
	
	private String token ="";
	
	private String theme ="";
	
	private boolean isEmpty =false;
	
	public User USER =null;
	
	public Company COMPANY =null;
	
	public String [] roleIds = null;
	
	// 所有角色json
	public String roleJsonString;
	
	//当前登录角色
	public Role ROLE;
	
	public TokenEntity(){
		this.init();
	}
	
	public TokenEntity(String _token){
		token=_token;
		this.init();
	}
	
	private void init(){
		USER=new User();
		COMPANY=new Company();
		isEmpty=false;
	}
	
	
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public class Role implements Serializable, Cloneable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 6121057068603174109L;
		private String roleId ="";
		private String roleName ="";
	    
		public String getRoleId() {
			return roleId;
		}

		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			// TODO 自动生成的方法存根
			return super.clone();
		}
	}


	public class User implements Serializable, Cloneable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4131318447692649992L;
		private String loginName ="";
		private String loginId ="";
	    private String name ="";
	    private String plainPassword ="";
	    private String password ="";
	    private String salt ="";
	    private String roles ="";
	    private Date registerDate =new Date();
	    protected String id ="";
	    private long loginTs =0;
	    private String email="";
	    
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getLoginName() {
			return loginName;
		}
		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPlainPassword() {
			return plainPassword;
		}
		public void setPlainPassword(String plainPassword) {
			this.plainPassword = plainPassword;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getSalt() {
			return salt;
		}
		public void setSalt(String salt) {
			this.salt = salt;
		}
		public String getRoles() {
			return roles;
		}
		public void setRoles(String roles) {
			this.roles = roles;
		}
		public Date getRegisterDate() {
			return registerDate;
		}
		public void setRegisterDate(Date registerDate) {
			this.registerDate = registerDate;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public long getLoginTs() {
			return loginTs;
		}
		public void setLoginTs(long loginTs) {
			this.loginTs = loginTs;
		}
		public String getLoginId() {
			return loginId;
		}
		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}
		
		@Override
		protected Object clone() throws CloneNotSupportedException {
			// TODO 自动生成的方法存根
			return super.clone();
		}
	}
    
    public class Company implements Serializable, Cloneable{
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 6960878424424662444L;

		private String company_id ="";
    	
    	private String company_name ="";
    	
    	private String company_code ="";

		public String getCompany_id() {
			return company_id;
		}

		public void setCompany_id(String company_id) {
			this.company_id = company_id;
		}

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public String getCompany_code() {
			return company_code;
		}

		public void setCompany_code(String company_code) {
			this.company_code = company_code;
		}
		
		@Override
		protected Object clone() throws CloneNotSupportedException {
			// TODO 自动生成的方法存根
			return super.clone();
		}
    }
    
    
    
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void Fill(TokenEntity t) {
		if(t!=null){
			try {
				USER =(User) t.USER.clone();
				COMPANY =(Company) t.COMPANY.clone();
			} catch (CloneNotSupportedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
    }
	
	
    
    public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	@Override
    protected Object clone() throws CloneNotSupportedException {
    	// TODO 自动生成的方法存根
    	return super.clone();
    }
}
