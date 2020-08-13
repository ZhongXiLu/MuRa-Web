package com.github.muraweb;

public class Util {

    /**
     * Get the repository name and its owner from the repository url.
     *
     * @param repoLink The GitHub repository url.
     * @return The repository name.
     */
    public static String getRepoName(String repoLink) {
        // Strip trailing slashes
        while (repoLink.charAt(repoLink.length() - 1) == '/') {
            repoLink = repoLink.substring(0, repoLink.length() - 1);
        }

        String[] parts = repoLink.split("/");

        return parts[parts.length - 2] + "/" + parts[parts.length - 1];
    }

}
