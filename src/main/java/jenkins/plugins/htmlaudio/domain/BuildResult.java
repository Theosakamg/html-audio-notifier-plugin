package jenkins.plugins.htmlaudio.domain;

import hudson.model.Result;


/**
 * The possible / interesting outcomes of a build.
 * 
 * @author Lars Hvile
 */
public enum BuildResult {

    SUCCESS_AFTER_FAILURE {
        @Override
        protected boolean matches(Result current, Result previous) {
            return (current == Result.SUCCESS || current == Result.UNSTABLE)
                && previous == Result.FAILURE;
        }
    },

    FAILURE {
        @Override
        protected boolean matches(Result current, Result previous) {
            return current == Result.FAILURE;
        }
    },

    UNSTABLE {
        @Override
        protected boolean matches(Result current, Result previous) {
            return current == Result.UNSTABLE;
        }
    },

    SUCCESS {
        @Override
        protected boolean matches(Result current, Result previous) {
            return current == Result.SUCCESS;
        }
    };

    /**
     * Returns a {@link BuildResult} matching a provided set of {@link Result}s or {@code null}.
     */
    public static BuildResult toBuildResult(Result current, Result previous) {
        for (BuildResult br : BuildResult.values()) {
            if (br.matches(current, previous)) {
                return br;
            }
        }
        return null;
    }

    protected abstract boolean matches(Result current, Result previous);
}
