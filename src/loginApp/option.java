package loginApp;

public enum option {
    Admin , Student;

    option(){}

        public String value(){
            return name();
        }

        public static option fromValue(String value){
        return valueOf(value);
        }

}
