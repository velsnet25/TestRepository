<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
  
  <!-- ========================= -->
  <!-- root element: perfmFootLabel -->
  <!-- ========================= -->
  <xsl:template match="PerfmDevSumRpt">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
               <fo:simple-page-master master-name="normal-page"
	                page-height="8.5in" page-width="11in"
	                margin-top="15mm" margin-bottom="10mm"
	                margin-left="0.6in" margin-right="0.6in">
	          <fo:region-before extent="18mm"/>
	          <fo:region-body margin-top="18mm" margin-bottom="3mm"/>
	          <fo:region-after extent="6mm"/>
	      </fo:simple-page-master>
    </fo:layout-master-set> 
          <fo:page-sequence master-reference="normal-page"
          initial-page-number="1" language="en" country="us">
          
          
          <!-- header ***************************************************** -->
	      <fo:static-content 
	     	              flow-name="xsl-region-before" 
	     	              font-family="Helvetica" 
	     	              line-height="10pt"
	     	              text-align="end">
	     	        <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
	     	            <fo:table-column column-number="1" column-width="proportional-column-width(1)"/>
	     	            <fo:table-column column-number="2" column-width="proportional-column-width(7)"/>
	     	            <fo:table-column column-number="3" column-width="proportional-column-width(1)"/>
	     	          <fo:table-body><fo:table-row>
	     	            <fo:table-cell><fo:block text-align="left" font-size="10pt"><xsl:value-of select="HomeDepot"/></fo:block></fo:table-cell>
	     	            <fo:table-cell><fo:block text-align="center" font-size="12pt" font-weight="bold"><xsl:value-of select="DevSummary"/></fo:block></fo:table-cell>
	     	            <fo:table-cell><fo:block text-align="right"></fo:block></fo:table-cell>
	     	          </fo:table-row></fo:table-body>
	     	          </fo:table>
	     	          
	     	<fo:table table-layout="fixed" border-collapse="collapse" width="100%">
		          <fo:table-column column-number="1" column-width="proportional-column-width(0.7)"/>
			  <fo:table-column column-number="2" column-width="proportional-column-width(1.8)"/>
			  <fo:table-column column-number="3" column-width="proportional-column-width(1.2)"/>
			  <fo:table-column column-number="4" column-width="proportional-column-width(1.8)"/>
			  <fo:table-column column-number="5" column-width="proportional-column-width(0.7)"/>
			  <fo:table-column column-number="6" column-width="proportional-column-width(1.8)"/>
	 	<fo:table-body><fo:table-row>
		          <fo:table-cell><fo:block text-align="left" font-size="8pt" space-before="8pt" text-indent="10pt"><xsl:value-of select="Name"/></fo:block></fo:table-cell>
		          <fo:table-cell><fo:block text-align="left" font-size="8pt" space-before="8pt" font-weight="bold"><xsl:value-of select="AssociateName"/></fo:block></fo:table-cell>
			  <fo:table-cell><fo:block text-align="left" font-size="8pt" space-before="8pt"><xsl:value-of select="BusinessUnit"/></fo:block></fo:table-cell>
			  <fo:table-cell><fo:block text-align="left" font-size="8pt" space-before="8pt" font-weight="bold"><xsl:value-of select="BusUnit"/></fo:block></fo:table-cell>
			  <fo:table-cell><fo:block text-align="left" font-size="8pt" space-before="8pt"><xsl:value-of select="Title"/></fo:block></fo:table-cell>
			  <fo:table-cell><fo:block text-align="left" font-size="8pt" space-before="8pt" font-weight="bold"><xsl:value-of select="JobTitle"/></fo:block></fo:table-cell>
		</fo:table-row></fo:table-body>
		</fo:table>
		
		
		
          
	     	<fo:table table-layout="fixed" border-collapse="collapse" width="100%">
		          <fo:table-column column-number="1" column-width="proportional-column-width(0.7)"/>
			  <fo:table-column column-number="2" column-width="proportional-column-width(1.8)"/>
			  <fo:table-column column-number="3" column-width="proportional-column-width(1.2)"/>
			  <fo:table-column column-number="4" column-width="proportional-column-width(1.8)"/>
			  <fo:table-column column-number="5" column-width="proportional-column-width(0.7)"/>
			  <fo:table-column column-number="6" column-width="proportional-column-width(1.8)"/>
	 	<fo:table-body><fo:table-row>
		          <fo:table-cell><fo:block text-align="left"></fo:block></fo:table-cell>
		          <fo:table-cell><fo:block text-align="left">__________________</fo:block></fo:table-cell>
		          <fo:table-cell><fo:block text-align="left"></fo:block></fo:table-cell>
		          <fo:table-cell><fo:block text-align="left">__________________</fo:block></fo:table-cell>
		          <fo:table-cell><fo:block text-align="left"></fo:block></fo:table-cell>
		          <fo:table-cell><fo:block text-align="left">__________________</fo:block></fo:table-cell>
		</fo:table-row></fo:table-body>
		</fo:table>
		
		<fo:block text-align="left" space-after="8pt" font-size="6pt" text-indent="0.8in">(<xsl:value-of select="asAppears"/>)</fo:block>
		  	    	      
	      </fo:static-content>
	      
          <!-- footer ******************************************************* -->
	      <fo:static-content 
	              flow-name="xsl-region-after" 
	              font-size="8pt" font-family="Helvetica" 
	              line-height="8pt"
	              text-align="end">
	         
	        <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
	            <fo:table-column column-number="1" column-width="proportional-column-width(2)"/>
	            <fo:table-column column-number="2" column-width="proportional-column-width(6)"/>
	            <fo:table-column column-number="3" column-width="proportional-column-width(1.5)"/>
	          <fo:table-body>
	          <fo:table-row>
		  <fo:table-cell><fo:block text-align="left"><xsl:value-of select="LabelType"/><xsl:value-of select="ReportDesc"/></fo:block></fo:table-cell>
		  <fo:table-cell><fo:block text-align="center">Page <fo:page-number/></fo:block></fo:table-cell>
		  <fo:table-cell><fo:block text-align="right"><xsl:value-of select="DocumentId"/><xsl:value-of select="DocumentIdNo"/></fo:block></fo:table-cell>
	          </fo:table-row>
	          </fo:table-body>
	          </fo:table>
	          
	        <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
	           <fo:table-column column-number="1" column-width="proportional-column-width(0.8)"/>
	           <fo:table-column column-number="2" column-width="proportional-column-width(7)"/>
	           <fo:table-column column-number="3" column-width="proportional-column-width(1.2)"/>
	           <fo:table-body>
	           <fo:table-row>
	           <fo:table-cell><fo:block text-align="left"><xsl:value-of select="ReportCodeSummary"/></fo:block></fo:table-cell>
		   <fo:table-cell><fo:block text-align="center"><xsl:value-of select="PageFooter"/></fo:block></fo:table-cell>
		   <fo:table-cell><fo:block text-align="right"><xsl:value-of select="Printedon"/><xsl:value-of select="PrintDate"/></fo:block></fo:table-cell>
	          </fo:table-row>
	          </fo:table-body>
	        </fo:table>
	       
	      </fo:static-content>
 		
 	  <!-- Body Content ******************************************************* -->
 	 
 		<fo:flow flow-name="xsl-region-body"  font-family="Helvetica">
 			
 			
 		  <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
 		   
 		     
 		     <fo:table-column column-number="1" column-width="proportional-column-width(9.8)" border="1pt solid blue"/>
	      	     <fo:table-body>
		     <fo:table-row height="6.5in">
			   <fo:table-cell>
			             		<fo:block space-after="0.05in"></fo:block>
			            
			            <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
			             			            
			             		<fo:table-column column-number="1" column-width="proportional-column-width(0.1)" />
			            	        <fo:table-column column-number="2" column-width="proportional-column-width(9.6)" border="1pt solid black"/>
			            		<fo:table-column column-number="3" column-width="proportional-column-width(0.1)" />
			             
	      	    				<fo:table-body>
			         			 <fo:table-row height="2in" >
			            			 <fo:table-cell><fo:block ></fo:block></fo:table-cell>
			            			 <fo:table-cell><fo:block ></fo:block>
			             
			             				<fo:table table-layout="fixed" border-collapse="collapse" width="100%">
			             
			              					<fo:table-column column-number="1" border="1pt solid blue" background-color="#E8E8E8" border-right-color="black" border-left-color="black"/>
				    		 			<fo:table-body>
				    						<fo:table-row height="0.15in">
			            						<fo:table-cell><fo:block font-size="8pt" space-before="0.04in" font-weight="bold" ><xsl:value-of select="LeaderSummary"/></fo:block></fo:table-cell>
			            					 </fo:table-row>
			            					</fo:table-body>
			            			 
			            				</fo:table>
			            				
			            				<fo:block font-size="8pt" space-before="1mm" ><xsl:value-of select="LeadSummRpt"/></fo:block>
			            				
			            			</fo:table-cell>
			            		        <fo:table-cell><fo:block></fo:block></fo:table-cell>
			           			</fo:table-row>
			            	     </fo:table-body>
			           	     </fo:table>  
			             			
			             			<fo:block  space-before="0.1in"></fo:block>
			             				
			             				<fo:table table-layout="fixed" border-collapse="collapse" width="100%">
				     			             			            
				     			             	<fo:table-column column-number="1" column-width="proportional-column-width(0.1)" />
				     			            	<fo:table-column column-number="2" column-width="proportional-column-width(4.7)" border="1pt solid black"/>
				     			                <fo:table-column column-number="3" column-width="proportional-column-width(0.2)" />
				     			                <fo:table-column column-number="4" column-width="proportional-column-width(4.7)" border="1pt solid black"/>
				     			                <fo:table-column column-number="5" column-width="proportional-column-width(0.1)" />
				     			             
				     	      	    			 <fo:table-body>
			         			 			<fo:table-row height="1.2in">
			         			 			<fo:table-cell><fo:block></fo:block></fo:table-cell>
			         			 			<fo:table-cell><fo:block></fo:block>
			         			 			
			         			 			
			         							 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
			             
			              								<fo:table-column column-number="1" border="1pt solid blue" background-color="#E8E8E8" border-right-color="black" border-left-color="black"/>
				    		 						<fo:table-body>
				    									<fo:table-row height="0.15in">
			            									<fo:table-cell><fo:block font-size="8pt" space-before="0.04in" font-weight="bold"><xsl:value-of select="KeyStrength"/></fo:block></fo:table-cell>
			            					 				</fo:table-row>
			            								</fo:table-body>
			            			 
			            							 </fo:table>
			            							 
			            							 <fo:block font-size="8pt" space-before="0.04in" font-weight="bold" ><xsl:value-of select="keyStrenRpt"/></fo:block>
			         			 			        			 				         			 	     			         			 				         			 			
			         			 			</fo:table-cell>
			         			 			<fo:table-cell><fo:block></fo:block></fo:table-cell>
			         			 			<fo:table-cell><fo:block></fo:block>
			         			 			
			         			
			         							 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
			             
			              								<fo:table-column column-number="1" border="1pt solid blue" background-color="#E8E8E8" border-right-color="black" border-left-color="black"/>
				    		 						<fo:table-body>
				    									<fo:table-row height="0.15in">
			            									<fo:table-cell><fo:block font-size="8pt" space-before="0.04in" font-weight="bold"><xsl:value-of select="DevNeed"/></fo:block></fo:table-cell>
			            					 				</fo:table-row>
			            								</fo:table-body>
			            			 
			            							 </fo:table>	 			
			         			 				
			         			 		<fo:block font-size="8pt" space-before="0.04in" font-weight="bold" ><xsl:value-of select="keyDevNeed"/></fo:block>				
			         			 			         			 			
			         			 			</fo:table-cell>
			         			 			<fo:table-cell><fo:block></fo:block></fo:table-cell>
			         			 			</fo:table-row>
			         			 		</fo:table-body>
			         			 	</fo:table>
			         			 	
			         			 	
			         				<fo:block  space-before="0.1in"></fo:block>
			             				
			             				<fo:table table-layout="fixed" border-collapse="collapse" width="100%">
				     			             			            
				     			             	<fo:table-column column-number="1" column-width="proportional-column-width(0.1)" />
				     			            	<fo:table-column column-number="2" column-width="proportional-column-width(4.7)" border="1pt solid black"/>
				     			                <fo:table-column column-number="3" column-width="proportional-column-width(0.2)" />
				     			                <fo:table-column column-number="4" column-width="proportional-column-width(4.7)" border="1pt solid black"/>
				     			                <fo:table-column column-number="5" column-width="proportional-column-width(0.1)" />
				     			             
				     	      	    			 <fo:table-body>
			         			 			<fo:table-row height="1.2in">
			         			 			<fo:table-cell><fo:block></fo:block></fo:table-cell>
			         			 			<fo:table-cell><fo:block></fo:block>
			         			 			
			         	
			         							 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
			             
			              								<fo:table-column column-number="1" border="1pt solid blue" background-color="#E8E8E8" border-right-color="black" border-left-color="black"/>
				    		 						<fo:table-body>
				    									<fo:table-row height="0.15in">
			            									<fo:table-cell><fo:block font-size="8pt" space-before="0.04in" font-weight="bold"><xsl:value-of select="DevPlan"/></fo:block></fo:table-cell>
			            					 				</fo:table-row>
			            								</fo:table-body>
			            								
			            										
			            			 
			            							 </fo:table>		 			
			         			 			
			         			 			        <fo:block font-size="8pt" space-before="0.04in" font-weight="bold" ><xsl:value-of select="DevTrain"/></fo:block>			 			
			         			 			        
			         			 			</fo:table-cell>
			         			 			<fo:table-cell><fo:block></fo:block></fo:table-cell>
			         			 			<fo:table-cell><fo:block></fo:block>
			         			 			
			         			 			
		
			         							 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
			             
			              								<fo:table-column column-number="1" border="1pt solid blue" background-color="#E8E8E8" border-right-color="black" border-left-color="black"/>
				    		 						<fo:table-body>
				    									<fo:table-row height="0.15in">
			            									<fo:table-cell><fo:block font-size="8pt" space-before="0.04in" font-weight="bold"><xsl:value-of select="NextPos"/></fo:block></fo:table-cell>
			            					 				</fo:table-row>
			            								</fo:table-body>
			            			 
			            							 </fo:table>
			            							 <fo:block space-before="1mm" font-size="6pt">(<xsl:value-of select="HourStore"/>)</fo:block>
			            							 
			       								 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
			             
			              							<fo:table-column column-number="1" column-width="proportional-column-width(2.4)" />
				 				            	        <fo:table-column column-number="2" column-width="proportional-column-width(1.6)"/>						
				    		 					<fo:table-body>  
				    		 					
				    		 					<fo:table-row>
				    		 					<fo:table-cell><fo:block font-size="8pt" space-before="1mm" font-weight="bold" text-align="left"><xsl:value-of select="Position"/></fo:block></fo:table-cell>
				    		 					<fo:table-cell><fo:block font-size="8pt" space-before="1mm" font-weight="bold" text-align="center"><xsl:value-of select="PotentialTime"/></fo:block></fo:table-cell>
				    		 					</fo:table-row>
				    		 					
				    							<fo:table-row>
				    		 					<fo:table-cell><fo:block font-size="8pt"  text-align="left"><xsl:value-of select="PositionName"/></fo:block></fo:table-cell>
				    		 					<fo:table-cell><fo:block font-size="8pt"  text-align="center"><xsl:value-of select="PotentialTiming"/></fo:block></fo:table-cell>
				    		 					</fo:table-row>					
				    		 					
				    		 					</fo:table-body>
				    		 					</fo:table>
			            							 
			            	 						<fo:table table-layout="fixed" border-collapse="collapse" width="100%">
			             
			              							<fo:table-column column-number="1" column-width="proportional-column-width(2.4)" />
				 				            	        <fo:table-column column-number="2" column-width="proportional-column-width(1.6)"/>						
				    		 					<fo:table-body>  
				    		 					<fo:table-row>
				    		 					<fo:table-cell><fo:block font-size="10pt" space-before="1mm" font-weight="bold" text-align="left"></fo:block></fo:table-cell>
				    		 					<fo:table-cell><fo:block font-size="10pt" space-before="1mm" font-weight="bold" text-align="center"></fo:block></fo:table-cell>
				    		 					</fo:table-row>
				    		 					</fo:table-body>
				    		 					</fo:table>						 
			            							 
			         			 			       	<fo:block font-weight="bold" space-before="1mm" font-size="8pt"><xsl:value-of select="PosComment"/></fo:block>	
			         			 			       	<fo:block font-weight="bold" space-before="1mm" font-size="8pt"><xsl:value-of select="PositionComment"/></fo:block>	
			         			 			       	
			         			 			       	
			         			 			
			         			 			</fo:table-cell>
			         			 			<fo:table-cell><fo:block></fo:block></fo:table-cell>
			         			 			</fo:table-row>
			         			 		</fo:table-body>
			         			 	</fo:table>			 	
			         			 	
			         			 <fo:block  space-before="0.1in"></fo:block>	
			         			 
			         				<fo:table table-layout="fixed" border-collapse="collapse" width="100%">
				 			             			            
				 			             		<fo:table-column column-number="1" column-width="proportional-column-width(0.1)" />
				 			            	        <fo:table-column column-number="2" column-width="proportional-column-width(9.6)" border="1pt solid black"/>
				 			            		<fo:table-column column-number="3" column-width="proportional-column-width(0.1)" />
				 			             
				 	      	    				<fo:table-body>
				 			         			 <fo:table-row height="0.5in">
				 			            			 <fo:table-cell><fo:block ></fo:block></fo:table-cell>
				 			            			 <fo:table-cell><fo:block ></fo:block>
				 			             
				 			             				<fo:table table-layout="fixed" border-collapse="collapse" width="100%">
				 			             
				 			              					<fo:table-column column-number="1" border="1pt solid blue" background-color="#E8E8E8" border-top-color="black" border-right-color="black" border-left-color="black"/>
				 				    		 			<fo:table-body>
				 				    						<fo:table-row height="0.15in">
				 			            						<fo:table-cell><fo:block font-size="8pt" space-before="0.04in" font-weight="bold"><xsl:value-of select="AssociateComment"/>(<xsl:value-of select="AttachSheet"/>)</fo:block></fo:table-cell>
				 			            					 </fo:table-row>
				 			            					</fo:table-body>
				 			            			 
				 			            				</fo:table>
				 			            				
				 			            				<fo:block font-size="8pt" space-before="1mm"><xsl:value-of select="AssociateCommentRpt"/></fo:block>
				 			            				
				 			            			</fo:table-cell>
				 			            		        <fo:table-cell><fo:block></fo:block></fo:table-cell>
				 			           			</fo:table-row>
				 			            	     </fo:table-body>
			           				 </fo:table>
			         			 
			         			 
			 	 			
			             		             
			             
			  </fo:table-cell>
			  		 		   					  			  
			         </fo:table-row>
			     </fo:table-body>
			</fo:table>
			
<fo:table table-layout="fixed" border-collapse="collapse" width="100%">
								 		   
								 		     
					<fo:table-column column-number="1" column-width="proportional-column-width(9.8)" border="1pt solid blue"/>
				 	<fo:table-body>
		 			<fo:table-row height="6.5in">
					<fo:table-cell><fo:block color="white"><xsl:value-of select="ReportContinue"/></fo:block>
					
					
					<fo:table table-layout="fixed" border-collapse="collapse" width="100%">
								             			            
								             		<fo:table-column column-number="1" column-width="proportional-column-width(3.2)" border="1pt solid black" border-right-color="white" />
								             		<fo:table-column column-number="2" column-width="proportional-column-width(3.2)" border="1pt solid black" />
								             		<fo:table-column column-number="3" column-width="proportional-column-width(3.4)" border="1pt solid black" border-left-color="white" />
								            	        <fo:table-body>
								         			 <fo:table-row height="2in" >
								            			 <fo:table-cell>
								            			 
								            			 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
								            			 
								            			 <fo:table-column column-number="1" column-width="proportional-column-width(0.7)"  />
												 <fo:table-column column-number="2" column-width="proportional-column-width(1.9)" background-color="#E8E8E8" border="1pt solid blue" />
								             			 <fo:table-column column-number="3" column-width="proportional-column-width(0.6)"  />
								            			 <fo:table-body>
								            			 <fo:table-row height="0.35in">
								            			 <fo:table-cell></fo:table-cell>
								            			 <fo:table-cell><fo:block font-size="10pt" text-align="center"  space-before="0.13in" font-weight="bold"><xsl:value-of select="PerfmCode"/></fo:block></fo:table-cell>
								            			 <fo:table-cell></fo:table-cell>
								            			 </fo:table-row>
								            			 </fo:table-body>
								            			 </fo:table>
								            			 
								            			 <fo:block space-before="0.2in"></fo:block>
								            			 
												 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
								            			 
								            			 <fo:table-column column-number="1" column-width="proportional-column-width(0.2)"  />
												 <fo:table-column column-number="2" column-width="proportional-column-width(2.8)"   />
								             			 <fo:table-column column-number="3" column-width="proportional-column-width(0.2)"  />
								            			 <fo:table-body>
								            			 <fo:table-row height="1.2in">
								            			 <fo:table-cell></fo:table-cell>
								            			 <fo:table-cell>
								            			 
								            			 
												 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
								            			 
								            			 <fo:table-column column-number="1" column-width="proportional-column-width(0.4)"   />
								            			 <fo:table-column column-number="2" column-width="proportional-column-width(2.4)" />
												 <fo:table-body>
												 
								            			 <fo:table-row height="0.4in" >
								            			 <fo:table-cell border="1pt solid black" ><xsl:if test="PerfmRateCode = TopPerformCode"><fo:block text-align="center" space-before="0.1in" font-size="15pt" font-weight="bold">X</fo:block></xsl:if></fo:table-cell>
								            			 <fo:table-cell border="1pt solid black"><fo:block font-size="10pt" font-weight="bold" text-indent="0.1in" space-before="0.1in"><xsl:value-of select="TopPerformer"/></fo:block></fo:table-cell>
								            			 </fo:table-row>
								            			 
								          			 <fo:table-row height="0.4in" >
								            			 <fo:table-cell border="1pt solid black"><xsl:if test="PerfmRateCode = ValueAssociateCode"><fo:block text-align="center" space-before="0.1in" font-size="15pt" font-weight="bold">X</fo:block></xsl:if></fo:table-cell>
								            			 <fo:table-cell border="1pt solid black"><fo:block font-size="10pt" font-weight="bold" text-indent="0.1in" space-before="0.1in"><xsl:value-of select="valuedAssociate"/></fo:block></fo:table-cell>
								            			 </fo:table-row>  
								            			 
							 					 <fo:table-row height="0.4in" >
								            			 <fo:table-cell  border="1pt solid black"><xsl:if test="PerfmRateCode = UnAcceptableCode"><fo:block text-align="center" space-before="0.1in" font-size="15pt" font-weight="bold">X</fo:block></xsl:if></fo:table-cell>
								            			 <fo:table-cell  border="1pt solid black"><fo:block font-size="10pt" font-weight="bold" text-indent="0.1in" space-before="0.1in"><xsl:value-of select="Unaccept"/></fo:block></fo:table-cell>
								            			 </fo:table-row>	            			 
								            			 
								            			 </fo:table-body>
								            			 </fo:table>		            			
								            			 
								            			     			 
								            			 </fo:table-cell>
								            			 <fo:table-cell></fo:table-cell>
								            			 </fo:table-row>
								            			 </fo:table-body>
								            			 </fo:table>	            			 
								            			 
								            			 
								            			
									            											            			 								            			 
								            			 								            			 
								            			 </fo:table-cell>
								            			 							            			 					            			 
								            			 <fo:table-cell>
								            			 
												 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
								            			 
								            			 <fo:table-column column-number="1" column-width="proportional-column-width(0.7)"  />
												 <fo:table-column column-number="2" column-width="proportional-column-width(1.9)" background-color="#E8E8E8" border="1pt solid blue" />
								             			 <fo:table-column column-number="3" column-width="proportional-column-width(0.6)"  />
								            			 <fo:table-body>
								            			 <fo:table-row height="0.35in">
								            			 <fo:table-cell></fo:table-cell>
								            			 <fo:table-cell><fo:block font-size="10pt" text-align="center"  space-before="0.13in" font-weight="bold"><xsl:value-of select="PotentialCode"/></fo:block></fo:table-cell>
								            			 <fo:table-cell></fo:table-cell>
								            			 </fo:table-row>
								            			 </fo:table-body>
								            			 </fo:table>		            			 
								            			 		
								            			 		
								     				 <fo:block space-before="0.2in"></fo:block>
								            			 
												 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
								            			 
								            			 <fo:table-column column-number="1" column-width="proportional-column-width(0.2)"  />
												 <fo:table-column column-number="2" column-width="proportional-column-width(2.8)"   />
								             			 <fo:table-column column-number="3" column-width="proportional-column-width(0.2)"  />
								            			 <fo:table-body>
								            			 <fo:table-row height="1.2in">
								            			 <fo:table-cell></fo:table-cell>
								            			 <fo:table-cell>
								            			 
								            			 
												 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
								            			 
								            			 <fo:table-column column-number="1" column-width="proportional-column-width(0.4)"   />
								            			 <fo:table-column column-number="2" column-width="proportional-column-width(2.4)" />
												 <fo:table-body>
												 
								            			 <fo:table-row height="0.4in" >
								            			 <fo:table-cell border="1pt solid black" ><xsl:if test="PotenRateCode = PromoteRateCode"><fo:block text-align="center" space-before="0.1in" font-size="15pt" font-weight="bold">X</fo:block></xsl:if></fo:table-cell>
								            			 <fo:table-cell border="1pt solid black"><fo:block font-size="10pt" font-weight="bold" text-indent="0.1in" space-before="0.1in"><xsl:value-of select="Promotable"/></fo:block></fo:table-cell>
								            			 </fo:table-row>
								            			 
								          			 <fo:table-row height="0.4in" >
								            			 <fo:table-cell border="1pt solid black"><xsl:if test="PotenRateCode = WellPositionRateCode"><fo:block text-align="center" space-before="0.1in" font-size="15pt" font-weight="bold">X</fo:block></xsl:if></fo:table-cell>
								            			 <fo:table-cell border="1pt solid black"><fo:block font-size="10pt" font-weight="bold" text-indent="0.1in" space-before="0.1in"><xsl:value-of select="WellPosition"/></fo:block></fo:table-cell>
								            			 </fo:table-row>  
								            			 
							 					 <fo:table-row height="0.4in" >
								            			 <fo:table-cell  border="1pt solid black"><xsl:if test="PotenRateCode = NotApplicableCode"><fo:block text-align="center" space-before="0.1in" font-size="15pt" font-weight="bold">X</fo:block></xsl:if></fo:table-cell>
								            			 <fo:table-cell  border="1pt solid black"><fo:block font-size="10pt" font-weight="bold" text-indent="0.1in" space-before="0.1in"><xsl:value-of select="NotApplicable"/></fo:block></fo:table-cell>
								            			 </fo:table-row>	            			 
								            			 
								            			
								            			</fo:table-body>
								            			 </fo:table>
								            			 
								            			 </fo:table-cell>
								            			 <fo:table-cell></fo:table-cell>
								            			 </fo:table-row>
												 </fo:table-body>
								            			 </fo:table>
								            			 		
								            			 		
								            			 		
								            			 		
								            			 
								            			 </fo:table-cell>
								            			 
								            			      			 
								            			 <fo:table-cell>
								            			 
								            			 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
								            			 <fo:table-column column-number="1" column-width="proportional-column-width(3.4)"  />
								            			 <fo:table-body>
								            			 <fo:table-row height="1in">
								            			 
								            			 <fo:table-cell border="1pt solid black">
								            			 
								            			 
								          			 <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
								            			 
								            			 <fo:table-column column-number="1" column-width="proportional-column-width(0.7)"  />
												 <fo:table-column column-number="2" column-width="proportional-column-width(1.9)" background-color="#E8E8E8" border="1pt solid blue" />
								             			 <fo:table-column column-number="3" column-width="proportional-column-width(0.6)"  />
								            			 <fo:table-body>
								            			 <fo:table-row height="0.35in">
								            			 <fo:table-cell></fo:table-cell>
								            			 <fo:table-cell><fo:block font-size="10pt" text-align="center" space-before="0.13in"  font-weight="bold"><xsl:value-of select="SummaryApproval"/></fo:block></fo:table-cell>
								            			 <fo:table-cell></fo:table-cell>
								            			 </fo:table-row>
								            			 </fo:table-body>
								            			 </fo:table>  
								            			 
								            			 <fo:block space-before="0.65in" text-indent="0.1in" font-size="12pt" ><xsl:value-of select="LeaderDate"/></fo:block>
								            			 
								            			 </fo:table-cell>
								              			 </fo:table-row>
								              			 </fo:table-body>
								            			 </fo:table>
								            			 
								            			 <fo:block space-before="0.7in" text-indent="0.1in" font-size="12pt" ><xsl:value-of select="AssociateDate"/></fo:block>
								            			 
								            			 </fo:table-cell>
			            			 				
			            			 				</fo:table-row>
											</fo:table-body>
	 										</fo:table>
					
					
					</fo:table-cell>
					</fo:table-row>
	 				</fo:table-body>
	 				</fo:table>			   	
			   	 
								 		
								 		  
							   			     
							   			    
			   			   
			   			     
			   	
			   	
			   			</fo:flow>
	       		</fo:page-sequence>	
			   							   		
			   		    
			      	 	 </fo:root>
				         </xsl:template>
				               </xsl:stylesheet>
				                   
				           
           

	                      
	                
	
                  
               