// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/AeWSDLException.java,v 1.2 2004/07/08 13:09:4
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
package org.activebpel.rt;

/**
 * Ae WSDL Related Exception class.
 */
public class AeWSDLException extends AeException
{
  /**
   * Constructor for AeWSDLExcepton.
   */
  public AeWSDLException()
  {
     super();
  }

  /**
   * Constructor for AeWSDLExcepton.
   * @param aInfo
   */
  public AeWSDLException(String aInfo)
  {
     super(aInfo);
  }

  /**
   * Constructor for AevWSDLException.
   * @param aRootCause
   */
  public AeWSDLException(Throwable aRootCause)
  {
     super(aRootCause);
  }
  
  /**
   * Constructor for AeWSDLExcepton.
   * @param aInfo
   * @param aRootCause
   */
  public AeWSDLException(String aInfo, Throwable aRootCause)
  {
     super(aInfo, aRootCause);
  }

}
