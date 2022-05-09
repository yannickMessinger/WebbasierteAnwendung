package de.hsrm.mi.web.projekt.validierung;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class BuntValidator implements ConstraintValidator<Bunt,String> {
    
   
    
    @Override
    public boolean isValid(String lieblingsfarbe, ConstraintValidatorContext context) {
        //ggf muss man erst den String noch komplett klein machen
        //hier auch überprüfung von regex??

        
        int len;
        String[] split;
        String pt1;
        String pt2;
        String pt3;

        final String HEX_WEBCOLOR_PATTERN = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";
        final Pattern pattern = Pattern.compile(HEX_WEBCOLOR_PATTERN);
        boolean korrekte_farbe;

        //eigentlich sollte null doch okay sein....??
        if(lieblingsfarbe == null){
            return false;
        }

        if(lieblingsfarbe.equals("")){
            return true;
        }

        len = lieblingsfarbe.length();

        Matcher matcher = pattern.matcher(lieblingsfarbe);
        korrekte_farbe = matcher.matches();

        if(!korrekte_farbe){
            return false;
        }
        
        if(len == 4){
           split = lieblingsfarbe.split("");
           return !(split[1].equals(split[2])) && !(split[1].equals(split[3])) && !(split[2].equals(split[3]));
        }

        if(len == 7){
            
            pt1 = lieblingsfarbe.substring(1, 3);
            pt2 = lieblingsfarbe.substring(3, 5);
            pt3 = lieblingsfarbe.substring(5, 7);

            return !pt1.equals(pt2) && !pt1.equals(pt3) && !pt2.equals(pt3);
           

        }

        return true;
    }
    
}
