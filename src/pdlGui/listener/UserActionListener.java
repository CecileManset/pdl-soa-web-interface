/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdlGui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import pdlGui.beans.InputBean;

/**
 *
 * @author Christelle
 */
public class UserActionListener implements ActionListener{
   public void processAction(ActionEvent arg0)
   throws AbortProcessingException {
      //access userData bean directly
      InputBean inputBean = (InputBean) FacesContext.getCurrentInstance().
         getExternalContext().getSessionMap().get("inputBean"); 
      inputBean.setInputData("hello");
      //inputBean.setInputText();
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }
}
