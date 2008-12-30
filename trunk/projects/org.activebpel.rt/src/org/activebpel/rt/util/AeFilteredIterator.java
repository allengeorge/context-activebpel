//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeFilteredIterator.java,v 1.2 2006/06/26 16:46:4
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
package org.activebpel.rt.util; 

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Allows base class for filtering contents of an iterator
 */
public abstract class AeFilteredIterator implements Iterator
{
   /** The iter that we're delegating to */
   private Iterator mDelegate;
   /** The next object to return */
   private Object mNext;
   /** Indicates if we have a next object. */
   private Boolean mHasNext;
   
   /**
    * Creates the filtered iterator the delegate iterator.
    * @param aDelegate
    */
   public AeFilteredIterator(Iterator aDelegate)
   {
      mDelegate = aDelegate;
   }

   /**
    * @see java.util.Iterator#hasNext()
    */
   public boolean hasNext()
   {
      checkForNext();
      
      return getHasNext().booleanValue();
   }

   /**
    * If we don't know if there's a next object, then read for it 
    * and record the object value. This will also set the hasNext Boolean
    * to indicate if we found a value since a null is an acceptable value.
    */
   protected void checkForNext()
   {
      if (getHasNext() == null)
      {
         setNext(readNextElement());
      }
   }

   /**
    * @see java.util.Iterator#next()
    */
   public Object next()
   {
      checkForNext();
      
      if (!getHasNext().booleanValue())
      {
         throw new NoSuchElementException();
      }
      
      Object next = getNext();
      setNext(null);
      setHasNext(null);
      return next;
   }
   
   /**
    * Reads the next element from the delegate iterator that is
    * accepted by the filter
    */
   protected Object readNextElement()
   {
      setHasNext(Boolean.FALSE);
      
      Object accepted = null;
      while(getDelegate().hasNext() && getHasNext().equals(Boolean.FALSE))
      {
         Object next = getDelegate().next();
         if (accept(next))
         {
            accepted = next;
            setHasNext(Boolean.TRUE);
         }
      }
      
      return accepted;
   }
   
   /**
    * Abstract accept method that is implemented by subclasses to provide the
    * type of filtering needed.
    * @param aObject
    */
   protected abstract boolean accept(Object aObject);

   /**
    * @see java.util.Iterator#remove()
    */
   public void remove()
   {
      throw new UnsupportedOperationException();
   }
   
   /**
    * @return Returns the next obj or null
    */
   protected Object getNext()
   {
      return mNext;
   }
   
   /**
    * @param aNext The next obj or null
    */
   protected void setNext(Object aNext)
   {
      mNext = aNext;
   }

   /**
    * @return Returns the delegate.
    */
   protected Iterator getDelegate()
   {
      return mDelegate;
   }

   /**
    * @param aDelegate The delegate to set.
    */
   protected void setDelegate(Iterator aDelegate)
   {
      mDelegate = aDelegate;
   }
   
   /**
    * Setter for the hasNext flag
    * @param aBoolean
    */
   protected void setHasNext(Boolean aBoolean)
   {
      mHasNext = aBoolean;
   }
   
   /**
    * Getter for the hasNext flag
    */
   protected Boolean getHasNext()
   {
      return mHasNext;
   }
}
 
