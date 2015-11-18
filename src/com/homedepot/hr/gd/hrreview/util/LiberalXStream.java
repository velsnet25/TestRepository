/**
 *
 * This program is proprietary to The Home Depot and is not to be
 * reproduced, used, or disclosed without permission of:
 *    
 *  The Home Depot
 *  2455 Paces Ferry Road, N.W.
 *  Atlanta, GA 30339-4053
 *
 * File Name: LiberalXStream.java
 * 
 * 
 */
package com.homedepot.hr.gd.hrreview.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class LiberalXStream extends XStream {
  
  protected MapperWrapper wrapMapper(MapperWrapper next) {


    return new MapperWrapper(next) {
    
     @SuppressWarnings("unchecked")
     public boolean shouldSerializeMember(
        Class definedIn,
        String fieldName) {


        if ( definedIn != Object.class ) {
          return super.shouldSerializeMember(definedIn, fieldName);
        } else {
            return false;
        }

      }
    };
  }
}