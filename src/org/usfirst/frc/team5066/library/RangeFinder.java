package org.usfirst.frc.team5066.library;

import java.util.Collections;
import java.util.List;

import edu.wpi.first.wpilibj.AnalogInput;

public class RangeFinder {
	
	// Add analog input, act as ultrasonic sensor
		private AnalogInput analogInput;
		
		/**
		 * 
		 * @param portNumber Plug in port number
		 * Instantiates an ultrasonic sensor at the given analog port num
		 */
		public RangeFinder(int portNumber) {
			analogInput = new AnalogInput(portNumber);
		}
		
		public double getVolts() {
			return analogInput.getVoltage();
		}
		
		/**
		 * 
		 * @return the range in millimeters, metric system numero uno
		 * This method is specific to our ultrasonic sensor (others may use a different conversion factor)
		 */
		public double findRangeMillimeters() {
			return analogInput.getVoltage() * 37.8;
		}
		
		/**
		 * 
		 * @return Find range in inches, imperial system sucks
		 * Just converts from millimeters to inches (i.e. relies on findRangeMillimeters() method)
		 */
		public double findRangeInches() {
			return findRangeMillimeters() / 25.4;
		}
		
		/**
		 * 
		 * @param factor
		 * @return testing purposes
		 * For evaluating the voltage multiplied by a certain factor.
		 */
		public double findRange(double factor) {
			return factor * analogInput.getVoltage();
		}
		
		/**
		 * Returns a sampled mean value of the ultrasonic range sensor in inches
		 * 
		 * @param samples - number of samples to take
		 * @return - inches of object away from sensor
		 */
		public double findSampledAverage(List<Double> samples){
			//sort from lowest to highest
			
			if(samples.isEmpty()){
				System.out.println("Can't find sampleing list");
				return 0;
			}
			if(samples.size()==1){
				return samples.get(0);
			}
			Collections.sort(samples);
			//find the median
			double Q1 = getQuartile(samples.subList(0, samples.size()/2));		
			double Q3 = getQuartile(samples.subList(samples.size()/2,samples.size()));
			double dropLeft = Q1 -(Q3-Q1);
			double dropRight = Q3 +(Q3-Q1);
			
			double returnValue = 0.0;
			int size = 0;
			for(int i=0; i<samples.size(); i++){
				if(samples.get(i) > dropLeft || samples.get(i) < dropRight){
					returnValue = returnValue + samples.get(i);
					size++;
				}	
				//if doesn't meeting ignore and continue;
			}
			return returnValue/size;
		}
		private double getQuartile(List<Double> quartileList){
			if(quartileList.size()%2==0){
				//even number
				return evenQuartile(quartileList);
			}
			//odd number
			return oddQuartile(quartileList);
			
		}
		
		private double evenQuartile(List<Double> sampleList){
			double a = sampleList.get((sampleList.size()/2));
			double b = sampleList.get(((sampleList.size()/2)+1));
			return (a+b)/2;
			
		}
		private double oddQuartile(List<Double> sampleList){
			return sampleList.get((sampleList.size()/2)-1);
		}
		
		
	
}
