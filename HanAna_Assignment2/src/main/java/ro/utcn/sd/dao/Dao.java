/*************************************************************************
 * ULLINK CONFIDENTIAL INFORMATION
 * _______________________________
 *
 * All Rights Reserved.
 *
 * NOTICE: This file and its content are the property of Ullink. The
 * information included has been classified as Confidential and may
 * not be copied, modified, distributed, or otherwise disseminated, in
 * whole or part, without the express written permission of Ullink.
 ************************************************************************/
package ro.utcn.sd.dao;

import java.util.List;

public interface Dao<T>
{
    T find(long id);
    
    List<T> findAll();

    void delete(T objectToDelete);

    void update(T objectToUpdate);

    void insert(T objectToCreate);

    void deleteById(long id);

    void closeConnection();
}
