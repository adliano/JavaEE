/*___________________________________________________
 * Author: Adriano Alves (aalves3)
 * Date  : Mar.09.2015
 * Course: Cs211E Spring of 2015
 * Objective : HW3 RMI Interface to work with
 *             server and client
 *____________________________________________________
 */
 

import java.rmi.*;

public interface RMIServerInterface extends Remote
{
   String[] states(String regex) throws RemoteException;
   String[] capitals(String regex) throws RemoteException;
   String getCapital(String state) throws RemoteException;
   String getState(String capital) throws RemoteException;
}
//END
