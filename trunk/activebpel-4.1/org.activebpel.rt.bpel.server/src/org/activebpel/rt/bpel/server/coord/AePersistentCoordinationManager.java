//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AePersistentCoordinationManager.java,v 1.9 2007/02/06 14:39:5
/*
 * Copyright (c) 2004-2006 Active Endpoints, Inc.
 *
 * This program is licensed under the terms of the GNU General Public License
 * Version 2 (the "License") as published by the Free Software Foundation, and 
 * the ActiveBPEL Licensing Policies (the "Policies").  A copy of the License 
 * and the Policies were distributed with this program.  
 *
 * The License is available at:
 * http: *www.gnu.org/copyleft/gpl.html
 *
 * The Policies are available at:
 * http: *www.activebpel.org/licensing/index.html
 *
 * Unless required by applicable law or agreed to in writing, this program is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied.  See the License and the Policies
 * for specific language governing the use of this program.
 */
package org.activebpel.rt.bpel.server.coord;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.coord.AeCoordinationDetail;
import org.activebpel.rt.bpel.coord.AeCoordinationException;
import org.activebpel.rt.bpel.coord.AeCoordinationNotFoundException;
import org.activebpel.rt.bpel.coord.IAeCoordinating;
import org.activebpel.rt.bpel.coord.IAeCreateContextRequest;
import org.activebpel.rt.bpel.coord.IAeProtocolState;
import org.activebpel.rt.bpel.server.engine.storage.AePersistentStoreFactory;
import org.activebpel.rt.bpel.server.engine.storage.IAeCoordinationStorage;

/**
 * Persistent Coordination manager implementation.
 */
public class AePersistentCoordinationManager extends AeCoordinationManager
{
   /** reference to the storage. */
   private IAeCoordinationStorage mStorage;

   /**
    * Default constructor.
    * @param aConfig
    */
   public AePersistentCoordinationManager(Map aConfig)
   {
      super(aConfig);      
   }
   
   /**
   * Gets called once after the manager has been instantiated. If the manager runs
   * into any kind of fatal error during create then it should throw an exception which will 
   * halt the startup of the application.
   */   
   public void create() throws Exception
   {
      setStorage(AePersistentStoreFactory.getInstance().getCoordinationStorage());
      getStorage().setCoordinationManager(this);
   }

   /**
    * Creates and registers the given context. The coordination id is normally null 
    * for Coordinators since this method will generate a new id.  
    * @param aCtxRequest
    * @param aId coordination id. Required for participants.
    * @param aRole
    * @return coordination context.
    * @throws AeCoordinationException
    */
   protected AeCoordinationContext createContext(IAeCreateContextRequest aCtxRequest, IAeCoordinationId aId,
         IAeProtocolState aInitState, int aRole) throws AeCoordinationException
   {
      String state = aInitState.getState();
      try
      {
         return getStorage().createContext( (AeCreateContextRequest)aCtxRequest, aId, state, aRole);
      }
      catch(Exception e)
      {
         throw new AeCoordinationException(e);
      }
   }   

   /**
    * Save the current state information.
    * @param aCoordinating
    */
   public void persistState(IAeCoordinating aCoordinating) throws AeCoordinationException
   {      
      try
      {
         AePersistentCoordinationId id = new AePersistentCoordinationId(aCoordinating.getProcessId(),
               aCoordinating.getCoordinationId());
         String state = aCoordinating.getState().getState();
         getStorage().updateCoordinationState(id, state);
      }
      catch(Exception e)
      {
         throw new AeCoordinationException(e);
      }      
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.coord.AeCoordinationManager#persistContext(org.activebpel.rt.bpel.coord.IAeCoordinating)
    */
   protected void persistContext(IAeCoordinating aCoordinating) throws AeCoordinationException   
   {
      try
      {
         AePersistentCoordinationId id = new AePersistentCoordinationId(aCoordinating.getProcessId(),
               aCoordinating.getCoordinationId());
         getStorage().updateCoordinationContext(id, (AeCoordinationContext) aCoordinating.getCoordinationContext());
      }
      catch(Exception e)
      {
         throw new AeCoordinationException(e);
      }      
   } 
   
   /**
    * Returns an iterator to coordinating activities matching the coordination id.
    * @param aCoordinationId
    * @return iterator of IAeCoordinating objects.
    */
   protected Iterator getCoordinatingIterator(String aCoordinationId) throws AeCoordinationNotFoundException
   {
      try
      {
         return getStorage().getCoordinations(aCoordinationId).iterator();
      }
      catch(Exception e)
      {
         AeException.logError(e, e.getMessage());
         throw new AeCoordinationNotFoundException(aCoordinationId, e);        
      }   
   }  

   /**
    * @see org.activebpel.rt.bpel.server.coord.AeCoordinationManager#getCoordinatingIterator(long)
    */
   protected Iterator getCoordinatingIterator(long aProcessId) throws AeCoordinationNotFoundException
   {
      try
      {
         return getStorage().getCoordinationsByProcessId(aProcessId).iterator();
      }
      catch(Exception e)
      {
         AeException.logError(e, e.getMessage());
         throw new AeCoordinationNotFoundException(String.valueOf(aProcessId), e);   
      }      
   }    
   
   /**
    * Returns a Coordination object given the process id and the location path.
    * @param aPid
    * @param aCoordinationId
    */
   protected IAeCoordinating getCoordinating(String aCoordinationId, long aPid) throws AeCoordinationNotFoundException
   {
      try
      {
         return getStorage().getCoordination(aCoordinationId, aPid);
      }
      catch(Exception e)
      {
         AeException.logError(e, e.getMessage());
         throw new AeCoordinationNotFoundException(aCoordinationId + ":" + String.valueOf(aPid), e); //$NON-NLS-1$  
      }   
   }

   /** 
    * Overrides method to return the coordinator detail from the SQL storage layer. 
    * @see org.activebpel.rt.bpel.coord.IAeCoordinationManager#getCoordinator(String)
    */
   public AeCoordinationDetail getCoordinatorDetail(long aChildProcessId) throws AeCoordinationNotFoundException
   {
      List rVal = null;
      try
      {
         rVal = getStorage().getCoordinatorDetail(aChildProcessId);
      }
      catch(Exception e)
      {
         AeException.logError(e, e.getMessage());
           
      }      
      if (rVal == null || rVal.size() == 0)
      {
         throw new AeCoordinationNotFoundException(String.valueOf(aChildProcessId)); 
      }
      else
      {
         return (AeCoordinationDetail) rVal.get(0);
      }
   }
   
   /** 
    * Overrides method to return a list of participants from the SQL storage layer. 
    * @see org.activebpel.rt.bpel.coord.IAeCoordinationManager#getParticipant(String)
    */
   public List getParticipantDetail(long aParentProcessId) throws AeCoordinationNotFoundException
   {
      List rList = null;
      try
      {
         rList = getStorage().getParticipantDetail(aParentProcessId);
      }
      catch(Exception e)
      {
         AeException.logError(e, e.getMessage());
           
      }      
      if (rList == null || rList.size() == 0)
      {
         throw new AeCoordinationNotFoundException(String.valueOf(aParentProcessId)); 
      }
      else
      {
         return rList;
      }
   }   
   
   /**
    * @return Returns the storage.
    */
   protected IAeCoordinationStorage getStorage()
   {
      return mStorage;
   }

   /**
    * @param aStorage The storage to set.
    */
   protected void setStorage(IAeCoordinationStorage aStorage)
   {
      mStorage = aStorage;
   }
}
