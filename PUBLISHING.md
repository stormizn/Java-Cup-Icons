# Publishing Java Cup Icons on JetBrains Marketplace

This guide follows the current Marketplace flow: the **first upload is manual**, and later versions
can be published with Gradle. The plugin is free, so no sales or payout setup is required.

Official references:

- [Uploading a new plugin](https://plugins.jetbrains.com/docs/marketplace/uploading-a-new-plugin.html)
- [Best practices for listing](https://plugins.jetbrains.com/docs/marketplace/best-practices-for-listing.html)
- [Publishing a plugin](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html)
- [Plugin upload API](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html)
- [Approval guidelines](https://plugins.jetbrains.com/legal/approval-guidelines)
- [Marketplace Developer Agreement](https://plugins.jetbrains.com/legal/developer-agreement)

## 1. One-time prerequisites

1. Create or sign in to a [JetBrains Account](https://account.jetbrains.com).
2. Sign in to the [Marketplace author area](https://plugins.jetbrains.com/author/me).
3. Accept the **JetBrains Marketplace Developer Agreement** and create a **Vendor profile**.
   Use `stormizn` as the vendor name on the listing.
4. Decide on the public source repository. The included `LICENSE` is MIT, and the Marketplace
   upload form requires a public source-code URL when an open-source license is selected.

## 2. Put the source on GitHub

Create a public repository, then from this project:

```powershell
git init
git add .
git commit -m "Java Cup Icons 1.0.2"
git branch -M main
git remote add origin https://github.com/stormizn/Java-Cup-Icons.git
git push -u origin main
```

Use the repository URL later as the Marketplace **Source code** link.

If you do not want to publish the source, replace `LICENSE` with your own EULA and choose the
corresponding license option during upload. Do not select MIT without a public source link.

## 3. Add the optional vendor links

The Marketplace page can show a vendor website, support email, homepage, and source/issue links.
The minimum required fields work without them, but a complete page looks more established.

For the next build, optionally add real values to `src/main/resources/META-INF/plugin.xml`:

```xml
<idea-plugin url="https://github.com/stormizn/Java-Cup-Icons">
    <vendor url="https://github.com/stormizn">stormizn</vendor>
</idea-plugin>
```

Only add URLs that are publicly reachable. Source, issue tracker, and support links can also be
added in the Marketplace admin panel after the plugin is created.

## 4. Build and verify the distribution

```powershell
.\gradlew.bat clean buildPlugin
```

Output:

```text
build/distributions/java-cup-icons-1.0.2.zip
```

Before uploading:

1. Install the ZIP in a clean IntelliJ IDEA 2026.3 instance.
2. Verify the Project View and editor tabs.
3. Verify that interfaces, enums, annotations, and nested interface classes are unchanged.
4. Verify the plugin icon appears in **Settings | Plugins**.
5. Check that the version in `build.gradle.kts` and `plugin.xml` matches the ZIP name.

Do not upload a ZIP built with a different plugin version or an old `build/` directory.

## 5. First upload: manual

1. Open the [Marketplace author area](https://plugins.jetbrains.com/author/me).
2. Open the account menu and choose **Upload plugin**.
3. Select the `stormizn` vendor profile.
4. Upload `java-cup-icons-1.0.2.zip`.
5. Fill in the form:
   - **License:** MIT
   - **Source code:** your public GitHub repository URL
   - **Tags:** choose the closest available values, typically `Icons`, `Appearance`, `Java`, and
     `Editor`. Do not select unrelated tags.
   - **Channel:** leave empty / Stable.
   - **Ads:** No.
   - **Pricing:** Free.
6. Submit the plugin for review.

The Marketplace team reviews new plugins manually. Expect a response within about two business
days. If they request changes, update the plugin or listing and resubmit.

## 6. Complete the plugin page after approval

The page is edited from the plugin's admin panel. Use the prepared assets in
[`docs/marketplace`](docs/marketplace).

### Description

Paste the contents of [`docs/marketplace/description.html`](docs/marketplace/description.html)
into the description field. The first 40 characters are the summary used on plugin cards, so keep
the opening sentence intact.

### Media

Upload the screenshots in this order:

1. [`screenshot-1.png`](docs/marketplace/screenshot-1.png) — the real Project View and editor.
2. [`screenshot-2.png`](docs/marketplace/screenshot-2.png) — the type-aware behavior.

Both are 1200×760 (the minimum recommended size is 1200×760) and use the real IDE. Suggested alt
text:

- `Project View with cup icons for a Java class and record; interface, enum, and annotation keep their theme icons.`
- `Classes and records use the cup; interfaces, enums, and annotations keep the theme icons.`

The [`hero.png`](docs/marketplace/hero.png) is for the README and social previews, not a substitute
for the two IDE screenshots.

### Details

- **Name:** Java Cup Icons
- **Vendor:** stormizn
- **Category/tags:** Icons, Appearance, Java, Editor (use only the tags offered and relevant)
- **Compatibility:** IntelliJ IDEA 2026.3
- **License:** MIT
- **Source code:** your public repository
- **Issue tracker:** your repository issues page (recommended)
- **Support email:** your public support address (recommended)
- **Getting started:** paste the installation steps from the description
- **Pricing:** Free

### Plugin icon

The 40 px and 80 px logos are already inside the ZIP at `META-INF/pluginIcon.png` and
`META-INF/pluginIcon@2x.png`. The 512 px version in `docs/marketplace/plugin-icon-512.png` can be
used for announcements or a website.

## 7. Later updates with Gradle

The first version must be uploaded manually. After the plugin exists on Marketplace:

1. Open **My Tokens** in your Marketplace profile and create a permanent token.
2. Keep the token out of the repository. Set it for the current shell:
   ```powershell
   $env:PUBLISH_TOKEN = "perm:YOUR_TOKEN"
   ```
3. Bump the version in both `build.gradle.kts` and
   `src/main/resources/META-INF/plugin.xml`.
4. Build and publish:
   ```powershell
   .\gradlew.bat clean buildPlugin
   .\gradlew.bat publishPlugin
   ```
5. Check the new version on the plugin page.

`build.gradle.kts` already reads `PUBLISH_TOKEN` from the environment. `publishPlugin` publishes to
the default Stable channel. To use a beta channel, add:

```kotlin
intellijPlatform {
    publishing {
        channels = listOf("beta")
    }
}
```

The version in a new upload must be higher than the previous one. Marketplace rejects duplicate
versions.

## 8. Signing

Marketplace signing is optional for the initial free release but recommended for a polished
distribution. If the plugin is not signed by the author, users may see a signature warning during
installation. The Marketplace still wraps the artifact with its own signing pipeline.

If you want author signing, follow
[Plugin Signing](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html) and configure
`signPlugin` with:

- `PUBLISH_TOKEN`
- `PRIVATE_KEY`
- `PRIVATE_KEY_PASSWORD`
- `CERTIFICATE_CHAIN`

Never commit the private key or token. This can be added after the first release without changing
the plugin's functionality.

## 9. Review checklist

Before submitting:

- [ ] Plugin name is short, descriptive, and does not contain the word “plugin”.
- [ ] Description starts with a 40-character English summary and has no unverifiable claims.
- [ ] Change notes are present for the uploaded version.
- [ ] ZIP contains `META-INF/plugin.xml`, the plugin JAR, and both plugin icons.
- [ ] The plugin installs and works in a clean IntelliJ IDEA 2026.3 instance.
- [ ] Screenshots are 1200×760, show the real IDE, and contain no personal information.
- [ ] Screenshots and description do not advertise unrelated products.
- [ ] License is selected; for MIT, the source URL is public and reachable.
- [ ] Tags match the plugin's actual purpose.
- [ ] No ads, telemetry, network calls, or hidden background behavior.
- [ ] The plugin does not bundle third-party libraries.

## 10. Common rejection reasons

- Missing or unreachable source-code link with an open-source license.
- Screenshots that are too small, unrelated, or show a different plugin.
- Description that does not explain what the plugin does.
- A vendor website that is unreachable.
- A name that conflicts with an existing Marketplace plugin.
- A version that was already uploaded.
- A plugin that does not install or work in the declared compatibility range.

## 11. Release checklist

For each release:

1. Update `CHANGELOG.md`.
2. Bump the version in `build.gradle.kts` and `plugin.xml`.
3. Run `.\gradlew.bat clean buildPlugin`.
4. Install the ZIP in a clean IDE.
5. Commit and tag the release.
6. Run `.\gradlew.bat publishPlugin` (or upload manually for the first release).
