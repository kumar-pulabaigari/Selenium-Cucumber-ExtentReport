package automation.report;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ConfigFileReadWrite {
    private static final Logger LOG = Logger.getLogger(ConfigFileReadWrite.class);

    /**
     * @param fileName
     * @param key
     * @param value
     * @return T
     * @author in01518
     */
    public synchronized static <T> Boolean write(String fileName, String key, T value) {
        Boolean successStatus = false;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        Properties prop = null;
        LOG.debug("write Method Invoked");
        try {
            LOG.debug("Inside try");
            try {
                LOG.debug("File : " + fileName + " Will Be Read");
                bufferedReader = new BufferedReader(new FileReader(fileName));
                LOG.debug("File : " + fileName + " Was Opened For Reading");
                /*
                 * If FileNotFoundException Not Encountered, Then Read
				 * Properties In File
				 */
                if (bufferedReader != null) {
					/* Instantiate Properties */
                    prop = new Properties();
                    LOG.debug("Properties Instance Was Created");
                    try {
                        prop.load(bufferedReader);
                        LOG.debug("properties from file : " + fileName
                                + " Were Read");
                    } catch (IOException e) {
                        LOG.error("Error In Reading File : " + fileName);

                        return successStatus;
                    }
                }
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(
                            fileName, false));
                    LOG.debug("File : " + fileName + " Was Opened For Writing");
                } catch (IOException e) {
                    LOG.debug("File : " + fileName
                            + " Could Not Be Opened For Writing ");

                    return successStatus;

                }

            }
			/* if File Does Not Exist, Then Create It and Open It for Write */ catch (FileNotFoundException e) {
                LOG.debug("File : " + fileName + " Could Not Be Found");
                LOG.debug("File : " + fileName + " Will Be Created");
                try {
                    bufferedWriter = new BufferedWriter(new PrintWriter(
                            FileUtils.openOutputStream(new File(fileName),
                                    false)));
                    LOG.debug("File : " + fileName + " Was Created For Writing");
                } catch (IOException e1) {
                    LOG.debug("File : " + fileName + " Could Not Be Created ");

                    return successStatus;
                }
            } catch (NullPointerException e) {

                return successStatus;
            }

			/*
			 * Instantiate Properties In Case, File Did Not Exist Previously,
			 * And Update New Properties
			 */
            if (prop == null) {
                prop = new Properties();
                LOG.debug("Properties Instance Was Created");
            }
            try {

                prop.put(key, value);
                LOG.debug("properties were updated for key = " + key
                        + " ,value = " + value);
                prop.store(bufferedWriter, null);
                LOG.debug("File : " + fileName
                        + " Was Updated For Added Property Key = " + key
                        + " ,value = " + value);
                successStatus = true;

            } catch (IOException e) {
                LOG.error("Error In Writing File : " + fileName);

                return successStatus;
            } catch (Exception e) {
                LOG.error("Error In Writing File : " + fileName);

                return successStatus;
            }

        }
		/* free resources after write */ finally {
            LOG.debug("Inside finally");
            LOG.debug("File Resources Will Be Closed");
            try {
                bufferedReader.close();
                LOG.debug("BufferedReader(bufferedReader) Instance closed");

            } catch (IOException e) {
                LOG.error("Error In Closing BufferedReader Instance");

            } catch (NullPointerException e) {
                LOG.error("Error In Closing BufferedReader Instance");

            }
            try {
                bufferedWriter.close();
                LOG.debug("BufferedWriter(bufferedWriter) Instance closed");
            } catch (IOException e) {
                LOG.error("Error In Closing BufferedWriter Instance");

            } catch (NullPointerException e) {

                LOG.error("Error In Closing BufferedWriter Instance");

            }
            LOG.info("successStatus being returned = " + successStatus);
            LOG.debug("Exiting write method");

        }

		/* return the status in case of no exception */
        return successStatus;

    }

    /**
     * @param fileName
     * @param key
     * @return keyValue
     * @author in01518
     */
    public synchronized static String read(String fileName, String key) {
        String keyValue = null;

        BufferedReader bufferedReader = null;
        Properties prop = null;
        //LOG.debug("read method invoked");
        //LOG.info("fileName = " + fileName);
        //LOG.info("key = " + key);
        try {
            //LOG.debug("Inside try");
            bufferedReader = new BufferedReader(new FileReader(fileName));
            //LOG.debug("File : " + fileName + " Was Opened For Reading");
            //LOG.debug("File.separator = " + File.separator);
            //LOG.debug("File.separatorChar = " + File.separatorChar);
			/* Instantiate Properties */
            prop = new Properties();
            //LOG.debug("Properties Instance Was Created");
            prop.load(bufferedReader);
            //LOG.debug("properties from file : " + fileName + " Were Read");
            keyValue = prop.getProperty(key);
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } catch (NullPointerException e) {

        } finally {
            //LOG.debug("Inside finally");
            try {
                bufferedReader.close();
                //LOG.debug("BufferedReader(bufferedReader) Instance Closed");
            } catch (IOException e) {


            } catch (NullPointerException e) {

            }
        }
        //LOG.debug("keyValue being returned = " + keyValue);
        //LOG.debug("Exiting read method");
        return keyValue;
    }


}
