package iloveyouboss;

// START:class
public class MatcherService {
// END:class
    class ProfileData { Profile retrieve(int id) { return null; } }
    class CriteriaData { Criteria retrieve(int id) { return null; } }

    ProfileData profileData = new ProfileData();
    CriteriaData criteriaData = new CriteriaData();

    // START:class
    public boolean matches(int profileId, int criteriaId) {
        var profile = profileData.retrieve(profileId);
        var criteria = criteriaData.retrieve(criteriaId);
        return new Matcher(criteria, profile.answers()).matches();
    }

    public int score(int profileId, int criteriaId) {
        var profile = profileData.retrieve(profileId);
        var criteria = criteriaData.retrieve(criteriaId);
        return new Matcher(criteria, profile.answers()).score();
    }
    // ...
}
// END:class
