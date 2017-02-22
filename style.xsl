<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <title>Decathlon results</title>
            </head>
            <body>
                <h2>Decathlon results</h2>
                <table border="1px solid black">
                    <tr bgcolor="#77aaff">
                        <th>Place</th>
                        <th>Final score</th>
                        <th>Name</th>
                        <xsl:for-each select="competition/events/event">
                            <th>
                                <xsl:value-of select="."/>
                            </th>
                        </xsl:for-each>
                    </tr>

                    <xsl:for-each select="competition/entry">
                        <tr>
                            <td>
                                <xsl:value-of select="rank"/>
                            </td>
                            <td>
                                <xsl:value-of select="score"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                            <xsl:for-each select="performances/*">
                                <td>
                                    <xsl:value-of select="."/>
                                </td>
                            </xsl:for-each>
                        </tr>
                    </xsl:for-each>

                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>


