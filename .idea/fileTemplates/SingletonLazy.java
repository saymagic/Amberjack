      #parse("File Statement.java")
      #if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
      #parse("File Header.java")
      public class ${NAME}{

        private static ${NAME} sInstance ;

        public static ${NAME} getInstance() {
          if (null == sInstance) {
            synchronized (${NAME}.class) {
                if (null == sInstance)
                    sInstance = new ${NAME}();
            }
          }
          return sInstance;
        }
    
        private ${NAME}() {
        }
      }