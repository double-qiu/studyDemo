package po;

public class Map {
	String country;
	String id;
	String citys;
	
	
	
	/**  
	 * Creates a new instance of Map.  
	 *  
	 * @param country
	 * @param id
	 * @param citys  
	 */  
	
	public Map(String country, String id, String citys) {
		super();
		this.country = country;
		this.id = id;
		this.citys = citys;
	}
	/**  
	 * country.  
	 *  
	 * @return  the country  
	 */
	public String getCountry() {
		return country;
	}
	/**  
	 * country.  
	 *  
	 * @param   country    the country to set    
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**  
	 * id.  
	 *  
	 * @return  the id  
	 */
	public String getId() {
		return id;
	}
	/**  
	 * id.  
	 *  
	 * @param   id    the id to set    
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**  
	 * citys.  
	 *  
	 * @return  the citys  
	 */
	public String getCitys() {
		return citys;
	}
	/**  
	 * citys.  
	 *  
	 * @param   citys    the citys to set    
	 */
	public void setCitys(String citys) {
		this.citys = citys;
	}
	
	
	
}
