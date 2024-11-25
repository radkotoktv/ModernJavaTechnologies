public class CourseScheduler {
    public static int maxNonOverlappingCourses(int[][] courses) {
        if(courses.length == 0) return 0;

        for (int i = 0; i < courses.length - 1; i++) {
            for (int j = i + 1; j < courses.length; j++) {
                if (courses[i][1] > courses[j][1]) {
                    int[] temp = courses[i];
                    courses[i] = courses[j];
                    courses[j] = temp;
                }
            }
        }

        int count = 1;
        int lastEnd = courses[0][1];
        for (int i = 1; i < courses.length; i++) {
            if (courses[i][0] >= lastEnd) {
                count++;
                lastEnd = courses[i][1];
            }
        }
        return count;
    }
}