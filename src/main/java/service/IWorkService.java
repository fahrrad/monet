package service;

import java.util.List;

import domain.Work;

/**
 * @author Ward
 * 
 *         methods to handle work objects
 * 
 */
public interface IWorkService {

	/**
	 * Gets a work by its id
	 * 
	 * @param id
	 *            the Id of the work to return
	 * @return work with id
	 */
	public Work getById(Long id);

	/**
	 * Gets a List of all the works that have name equals {@link name}
	 * 
	 * @param name
	 *            the name of the work you want to look for
	 * @return a list of works that go by the name you looked for, or an empty
	 *         list if none were found
	 */
	public List<Work> getByName(String name);

	/**
	 * saves a work, either by creating it ( if the id is not in the database ),
	 * or saving it
	 * 
	 * @param work
	 * @return
	 */
	public Long insertOrUpdate(Work work);

	/**
	 * Removes work with Id from the database
	 * 
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * @return a list of all the works in the database
	 */
	public List<Work> getAll();

}
