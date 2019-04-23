package org.odk.collect.android.mne.onboarding;

public class UserRepository {
    private final UserLocalSource localSource;
    private final UserRemoteSource remoteSource;
    private static UserRepository INSTANCE = null;


    public static UserRepository getInstance(UserLocalSource localSource, UserRemoteSource remoteSource) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository(localSource, remoteSource);
                }
            }
        }
        return INSTANCE;
    }

    public UserRepository(UserLocalSource localSource, UserRemoteSource remoteSource) {
        this.localSource = localSource;
        this.remoteSource = remoteSource;
    }



}
