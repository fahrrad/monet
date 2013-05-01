package service;

import domain.Collection;

public interface ICollecionService {
	
	public Collection getById(Long id);
	
	public Long insertOrUpdate(Collection collection);
	
	public Collection getByName(String name);
	
	public void delete(Long id);

}
